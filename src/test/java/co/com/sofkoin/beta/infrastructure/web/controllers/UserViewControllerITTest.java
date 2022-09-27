package co.com.sofkoin.beta.infrastructure.web.controllers;

import co.com.sofkoin.beta.application.commons.views.CryptoPriceView;
import co.com.sofkoin.beta.application.commons.views.UserDBView;
import co.com.sofkoin.beta.application.commons.views.UserDashboardView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import co.com.sofkoin.beta.application.usecases.FindAllUsersUseCase;
import co.com.sofkoin.beta.application.usecases.FindUserByIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "MONGO_PASSWORD=password",
        "MONGO_USER=user",
        "RABBIT_HOST=host",
        "RABBIT_PASSWORD=rabbit_password",
        "RABBIT_PORT=5672",
        "RABBIT_USERNAME=adfassd",
        "RABBIT_VIRTUAL_HOST=asfdas"
})
class UserViewControllerITTest {
    @MockBean
    private DomainViewRepository domainViewRepository;

    @Autowired
    private FindAllUsersUseCase findAllUsersUseCase;

    @Autowired
    private FindUserByIdUseCase findUserByIdUseCase;

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("#findUserById should return a UserDashboardView with the data of the user linked to the given id.")
    public void findUserById_ShouldReturnTheUserWithTheGivenId_WhenSuccessful() {
        // Arrange
        UserDBView user = new UserDBView(
                UUID.randomUUID().toString(),
                "user@gmail.com",
                "user name",
                "3454299223",
                "http://somewhere.com",
                "MANUAL",
                1000.0,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet()
        );

        Set<CryptoPriceView> userCryptoPriceViews = user.getCryptos()
                .stream()
                .map(view -> new CryptoPriceView(view.getSymbol(), view.getAmount(), Math.random()))
                .collect(Collectors.toSet());

        UserDashboardView dashboardView = new UserDashboardView(
                user.getUserId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getAvatarUrl(),
                user.getCurrentCash(),
                user.getMessages(),
                userCryptoPriceViews,
                user.getActivities(),
                user.getTransactions()
        );

        BDDMockito
                .when(this.domainViewRepository.findByUserId(user.getUserId()))
                .thenReturn(Mono.just(user));

        // Act and Assert
        this.webClient.get()
                .uri(builder ->
                    builder.path("/view/user/{userId}")
                            .build(Map.of("userId", user.getUserId()))
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDashboardView.class).isEqualTo(dashboardView)
                .consumeWith(System.out::println);

        BDDMockito
                .verify(this.domainViewRepository, BDDMockito.times(1))
                .findByUserId(user.getUserId());
    }

    @Test
    @DisplayName("#findUserById should throw an IllegalArgumentException when the given id doesn't belong to any user.")
    public void findUserById_ShouldThrowAnIllegalArgumentException_WhenUnsuccessful() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        BDDMockito
                .when(this.domainViewRepository.findByUserId(userId))
                .thenReturn(Mono.error(new IllegalArgumentException("The user with the given id does not exist.")));

        // Act and Assert
        this.webClient.get()
                .uri(builder ->
                        builder.path("/view/user/{userId}")
                                .build(Map.of("userId", userId))
                )
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                    .jsonPath("$.errorMessage").exists()
                .consumeWith(System.out::println);

        BDDMockito
                .verify(this.domainViewRepository, BDDMockito.times(1))
                .findByUserId(userId);
    }

    @Test
    @DisplayName("#findAllUsers should return a List of UserDashboardView when there are users saved in the database.")
    public void findAllUsers_ShouldReturnAListOfUserDashboardView_WhenSuccessful() {
        // Arrange
        UserDBView user = new UserDBView(
                UUID.randomUUID().toString(),
                "user@gmail.com",
                "user name",
                "3454299223",
                "http://somewhere.com",
                "MANUAL",
                1000.0,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet()
        );

        BDDMockito
                .when(this.domainViewRepository.findAllUsers())
                .thenReturn(Flux.just(user, user));

        // Act and Assert
        this.webClient.get()
                .uri(builder ->
                        builder.path("/view/user/all")
                                .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDashboardView.class).hasSize(2)
                .consumeWith(System.out::println);

        BDDMockito
                .verify(this.domainViewRepository, BDDMockito.times(1))
                .findAllUsers();
    }

    @Test
    @DisplayName("#findAllUsers should throw an IllegalStateException when the database returns no users.")
    public void findAllUsers_ShouldThrowAnIllegalStateException_WhenUnsuccessful() {
        // Arrange
        BDDMockito
                .when(this.domainViewRepository.findAllUsers())
                .thenReturn(Flux.error(new IllegalStateException("There are no users saved in the database.")));

        // Act and Assert
        this.webClient.get()
                .uri(builder ->
                        builder.path("/view/user/all")
                                .build()
                )
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                    .jsonPath("$.errorMessage").exists()
                .consumeWith(System.out::println);

        BDDMockito
                .verify(this.domainViewRepository, BDDMockito.times(1))
                .findAllUsers();
    }

}
package co.com.sofkoin.beta.infrastructure.web.controllers;

import co.com.sofkoin.beta.application.commons.views.MarketView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import co.com.sofkoin.beta.application.usecases.FindAllMarketsUseCase;
import co.com.sofkoin.beta.application.usecases.FindMarketByIdUseCase;
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

import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

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
class MarketViewControllerITTest {
    @MockBean
    private DomainViewRepository domainViewRepository;

    @Autowired
    private FindAllMarketsUseCase findAllMarketsUseCase;

    @Autowired
    private FindMarketByIdUseCase findMarketByIdUseCase;

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("#getMarketById should return a MarketView when the given id belongs to a Market.")
    public void getMarketById_ShouldReturnAMarketView_WhenSuccessful() {
        // Arrange
        MarketView marketView = new MarketView(
                UUID.randomUUID().toString(),
                new HashSet<>(),
                new HashSet<>()
        );
        BDDMockito
                .when(this.domainViewRepository.findMarketById(marketView.getMarketId()))
                .thenReturn(Mono.just(marketView));

        // Act and Assert
        this.webClient.get()
                .uri(builder ->
                        builder.path("/view/market/{marketId}")
                                .build(Map.of("marketId", marketView.getMarketId()))
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                    .jsonPath("$.marketId").isEqualTo(marketView.getMarketId())
                    .jsonPath("$.offers").isArray();

        BDDMockito.verify(this.domainViewRepository, BDDMockito.times(1))
                .findMarketById(marketView.getMarketId());
    }

    @Test
    @DisplayName("#getMarketById should return an error when the given id doesn't belong to any markets in the database.")
    public void getMarketById_ShouldThrowAnIllegalArgumentException_WhenUnsuccessful() {
        // Arrange
        String marketId = UUID.randomUUID().toString();
        BDDMockito
                .when(this.domainViewRepository.findMarketById(BDDMockito.anyString()))
                .thenReturn(Mono.error(new IllegalArgumentException("The given id does not belong to any market.")));

        // Act and Assert
        this.webClient.get()
                .uri(builder ->
                        builder.path("/view/market/{marketId}")
                                .build(Map.of("marketId", marketId))
                )
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.errorMessage").exists()
                .consumeWith(System.out::println);

        BDDMockito.verify(this.domainViewRepository, BDDMockito.times(1))
                .findMarketById(marketId);
    }

    @Test
    @DisplayName("#getAllMarkets should return a List of MarketView when there are markets in the database.")
    public void getAllMarkets_ShouldReturnAListOfMarketViews_WhenSuccessful() {
        // Arrange
        MarketView marketView = new MarketView(
                UUID.randomUUID().toString(),
                new HashSet<>(),
                new HashSet<>()
        );
        BDDMockito
                .when(this.domainViewRepository.findAllMarkets())
                .thenReturn(Flux.just(marketView, marketView));

        // Act and Assert
        this.webClient.get()
                .uri(builder ->
                        builder.path("/view/market/all")
                                .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MarketView.class)
                .consumeWith(System.out::println);

        BDDMockito.verify(this.domainViewRepository, BDDMockito.times(1))
                .findAllMarkets();
    }

    @Test
    @DisplayName("#getAllMarkets should return an IllegalStateException when no markets exist in the database.")
    public void getAllMarkets_ShouldReturnAnErrorMessage_WhenUnsuccessful() {
        // Arrange
        BDDMockito
                .when(this.domainViewRepository.findAllMarkets())
                .thenReturn(Flux.error(new IllegalStateException("There are no markets created.")));

        // Act and Assert
        this.webClient.get()
                .uri(builder ->
                        builder.path("/view/market/all")
                                .build()
                )
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                    .jsonPath("$.errorMessage").exists()
                .consumeWith(System.out::println);

        BDDMockito.verify(this.domainViewRepository, BDDMockito.times(1))
                .findAllMarkets();
    }
}
package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.views.UserView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindAllUsersUseCaseTest {

  @Mock
  private DomainViewRepository repository;

  @InjectMocks
  private FindAllUsersUseCase useCase;

  @Test
  void findUserByUseCase() {
    UserView userView = new UserView(
            "123812",
            "mail@mail.com",
            "Jose David",
            "2381823",
            "http://image.png",
            "GMAIL",
            0.0,
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet()
    );

    UserView userView1 = new UserView();
    UserView userView2 = new UserView();
    UserView userView3 = new UserView();

    BDDMockito
            .when(repository.findAllUsers())
            .thenReturn(Flux.just(userView, userView1, userView2, userView3));

    Mono<List<UserView>> usersFound = useCase.get().collectList();

    StepVerifier
            .create(usersFound)
            .expectSubscription()
            .assertNext(users -> {
              UserView firstUser = users.get(0);

              assertEquals(4, users.size());
              assertEquals(userView.getUserId(), firstUser.getUserId());
              assertEquals(userView.getEmail(), firstUser.getEmail());
              assertEquals(userView.getFullName(), firstUser.getFullName());
            })
            .expectComplete()
            .log()
            .verify();


    BDDMockito
            .verify(repository)
            .findAllUsers();
  }
}
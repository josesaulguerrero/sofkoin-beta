package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.views.UserDBView;
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
    UserDBView userDBView = new UserDBView(
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

    UserDBView userDBView1 = new UserDBView();
    UserDBView userDBView2 = new UserDBView();
    UserDBView userDBView3 = new UserDBView();

    BDDMockito
            .when(repository.findAllUsers())
            .thenReturn(Flux.just(userDBView, userDBView1, userDBView2, userDBView3));

    Mono<List<UserDBView>> usersFound = useCase.get().collectList();

    StepVerifier
            .create(usersFound)
            .expectSubscription()
            .assertNext(users -> {
              UserDBView firstUser = users.get(0);

              assertEquals(4, users.size());
              assertEquals(userDBView.getUserId(), firstUser.getUserId());
              assertEquals(userDBView.getEmail(), firstUser.getEmail());
              assertEquals(userDBView.getFullName(), firstUser.getFullName());
            })
            .expectComplete()
            .log()
            .verify();


    BDDMockito
            .verify(repository)
            .findAllUsers();
  }
}
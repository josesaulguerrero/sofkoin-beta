package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.views.UserDBView;
import co.com.sofkoin.beta.application.commons.views.UserDashboardView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class FindUserByIdUseCaseTest {

  @Mock
  private DomainViewRepository repository;

  @InjectMocks
  private FindUserByIdUseCase useCase;

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

    BDDMockito
      .when(repository.findByUserId(BDDMockito.anyString()))
      .thenReturn(Mono.just(userDBView));

    Mono<UserDashboardView> userFound = useCase.apply(Mono.just(userDBView.getUserId()));

//    StepVerifier
//            .create(userFound)
//            .expectSubscription()
//            .assertNext(user -> {
//              assertEquals(userDBView.getUserId(), user.getUserId());
//              assertEquals(userDBView.getEmail(), user.getEmail());
//              assertEquals(userDBView.getFullName(), user.getFullName());
//            })
//            .expectComplete()
//            .log()
//            .verify();
//
//
//    BDDMockito
//      .verify(repository)
//      .findByUserId(BDDMockito.anyString());
  }
}
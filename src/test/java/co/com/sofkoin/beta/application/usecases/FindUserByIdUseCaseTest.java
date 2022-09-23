package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.views.UserView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindUserByIdUseCaseTest {

  @Mock
  private DomainViewRepository repository;

  @InjectMocks
  private FindUserByIdUseCase useCase;

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

    BDDMockito
      .when(repository.findByUserId(BDDMockito.anyString()))
      .thenReturn(Mono.just(userView));

    Mono<UserView> userFound = useCase.apply(Mono.just(userView.getUserId()));

    StepVerifier
            .create(userFound)
            .expectSubscription()
            .assertNext(user -> {
              assertEquals(userView.getUserId(), user.getUserId());
              assertEquals(userView.getEmail(), user.getEmail());
              assertEquals(userView.getFullName(), user.getFullName());
            })
            .expectComplete()
            .log()
            .verify();


    BDDMockito
      .verify(repository)
      .findByUserId(BDDMockito.anyString());
  }
}
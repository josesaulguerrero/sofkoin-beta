package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.views.CryptoView;
import co.com.sofkoin.beta.application.commons.views.UserDBView;
import co.com.sofkoin.beta.application.commons.views.UserDashboardView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import co.com.sofkoin.beta.application.gateways.rest.RestCryptoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FindUserByIdUseCaseTest {

  @Mock
  private DomainViewRepository repository;

  @Mock
  private GetCryptoPriceUseCase getCryptoPriceUseCase;

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
            "MANUAL",
            0.0,
            Collections.emptySet(),
            Set.of(new CryptoView("BTC", 1.1)),
            Collections.emptySet(),
            Collections.emptySet()
    );

    var cryptoResponse = new RestCryptoResponse();
    var symbolList = List.of("BTC", "ETH", "ADA", "SOL");

    symbolList.forEach(symbol -> {
      var map = Map.of("USD", 19500.0);
      cryptoResponse.put(symbol, map);
    } );


      BDDMockito
      .when(repository.findByUserId(BDDMockito.anyString()))
      .thenReturn(Mono.just(userDBView));

    BDDMockito
      .when(getCryptoPriceUseCase.get())
      .thenReturn(Mono.just(cryptoResponse));

    Mono<UserDashboardView> userFound = useCase.apply(Mono.just(userDBView.getUserId()));

    StepVerifier
            .create(userFound)
            .expectSubscription()
            .assertNext(user -> {
              System.out.println(user.toString());
              assertEquals(userDBView.getUserId(), user.getUserId());
              assertEquals(userDBView.getEmail(), user.getEmail());
              assertEquals(userDBView.getFullName(), user.getFullName());
            })
            .verifyComplete();

    BDDMockito
      .verify(repository)
      .findByUserId(BDDMockito.anyString());

  }

}
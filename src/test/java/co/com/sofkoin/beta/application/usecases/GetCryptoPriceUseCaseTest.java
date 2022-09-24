package co.com.sofkoin.beta.application.usecases;


import co.com.sofkoin.beta.application.gateways.rest.RestClient;
import co.com.sofkoin.beta.application.gateways.rest.RestCryptoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class GetCryptoPriceUseCaseTest {

    @Mock
    RestClient restClient;

    @InjectMocks
    GetCryptoPriceUseCase useCase;

    @Test
    void getCryptoPriceUseCaseTest(){

        var cryptoResponse = new RestCryptoResponse();
        var symbolList = List.of("BTC", "ETH", "ADA", "SOL");

        symbolList.forEach(symbol -> {
            var map = Map.of("USD", 19500.0);
            cryptoResponse.put(symbol, map);
        } );

        BDDMockito.when(restClient.getCryptoResponse()).thenReturn(Mono.just(cryptoResponse));

        StepVerifier.create(useCase.get())
                .expectSubscription()
                .expectNextMatches(restCryptoResponse -> restCryptoResponse.size() == 4)
                .verifyComplete();

        BDDMockito.verify(restClient, BDDMockito.times(1)).getCryptoResponse();

    }

}
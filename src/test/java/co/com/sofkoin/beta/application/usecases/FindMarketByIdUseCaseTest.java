package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.views.MarketView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
class FindMarketByIdUseCaseTest {

    @Mock
    DomainViewRepository repository;

    @InjectMocks
    FindMarketByIdUseCase useCase;

    @Test
    void findMarketByIdUseCase(){

        final String MARKET_ID = "f09d2b66-2466-4e51-be1d-682cae11c7c5";

        var marketViewReturn = new MarketView(MARKET_ID, new HashSet<>(), new HashSet<>());

        BDDMockito.when(repository.findMarketById(MARKET_ID)).thenReturn(Mono.just(marketViewReturn));

        var publisher = useCase.apply(Mono.just(MARKET_ID));

        StepVerifier.create(publisher)
                .expectSubscription()
                .expectNext(marketViewReturn)
                .verifyComplete();

        BDDMockito.verify(repository, BDDMockito.times(1)).findMarketById(MARKET_ID);


    }

}
package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.views.MarketView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
class FindAllMarketsUseCaseTest {
    
    @Mock
    DomainViewRepository repository;
    
    @InjectMocks
    FindAllMarketsUseCase useCase;
    
    @Test
    void findAllMarketsUseCaseTest(){

        var colombiaMarket = new MarketView("1", new HashSet<>(), new HashSet<>());
        var unitedStatesMarket =  new MarketView("2", new HashSet<>(), new HashSet<>());

        var marketsFLux = Flux.just(colombiaMarket, unitedStatesMarket);

        BDDMockito.when(repository.findAllMarkets()).thenReturn(marketsFLux);

        var publisher = useCase.get();

        StepVerifier.create(publisher)
                .expectSubscription()
                .expectNext(colombiaMarket, unitedStatesMarket)
                .verifyComplete();

        BDDMockito.verify(repository, BDDMockito.times(1)).findAllMarkets();
        
    }
    
}
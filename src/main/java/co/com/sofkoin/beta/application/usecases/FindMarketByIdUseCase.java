package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.usecases.UseCase;
import co.com.sofkoin.beta.application.commons.views.MarketView;
import reactor.core.publisher.Mono;

public class FindMarketByIdUseCase implements UseCase<String, MarketView> {
    @Override
    public Mono<MarketView> apply(Mono<String> arg) {
        return null;
    }
}

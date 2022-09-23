package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.usecases.UseCase;
import co.com.sofkoin.beta.application.commons.views.MarketView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class FindMarketByIdUseCase implements UseCase<String, MarketView> {

    private final DomainViewRepository repository;

    @Override
    public Mono<MarketView> apply(Mono<String> markerId) {
        return markerId.flatMap(repository::findMarketById);
    }

}

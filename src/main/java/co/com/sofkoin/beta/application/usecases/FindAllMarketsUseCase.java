package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.usecases.SupplierUseCase;
import co.com.sofkoin.beta.application.commons.views.MarketView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@AllArgsConstructor
public class FindAllMarketsUseCase implements SupplierUseCase<MarketView> {

    private final DomainViewRepository repository;

    @Override
    public Flux<MarketView> get() {
        return repository.findAllMarkets();
    }
}

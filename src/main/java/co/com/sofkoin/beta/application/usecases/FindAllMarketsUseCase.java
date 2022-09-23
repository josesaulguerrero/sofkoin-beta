package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.usecases.SupplierUseCase;
import co.com.sofkoin.beta.application.commons.views.MarketView;
import reactor.core.publisher.Flux;

public class FindAllMarketsUseCase implements SupplierUseCase<MarketView> {
    @Override
    public Flux<MarketView> get() {
        return null;
    }
}

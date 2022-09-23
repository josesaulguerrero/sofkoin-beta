package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.usecases.SupplierUseCase;
import co.com.sofkoin.beta.application.commons.usecases.UseCase;
import co.com.sofkoin.beta.application.commons.views.UserView;
import co.com.sofkoin.beta.application.commons.views.View;
import reactor.core.publisher.Flux;

public class FindAllUsersUseCase implements SupplierUseCase<UserView> {
    public Flux<UserView> get() {
        return null;
    }
}

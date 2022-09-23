package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.usecases.SupplierUseCase;
import co.com.sofkoin.beta.application.commons.usecases.UseCase;
import co.com.sofkoin.beta.application.commons.views.UserView;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

public class FindUserByIdUseCase implements UseCase<String, UserView> {
    @Override
    public Mono<UserView> apply(Mono<String> arg) {
        return null;
    }
}

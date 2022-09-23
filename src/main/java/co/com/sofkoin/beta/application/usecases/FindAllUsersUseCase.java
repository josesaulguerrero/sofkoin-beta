package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.usecases.SupplierUseCase;
import co.com.sofkoin.beta.application.commons.usecases.UseCase;
import co.com.sofkoin.beta.application.commons.views.UserView;
import co.com.sofkoin.beta.application.commons.views.View;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class FindAllUsersUseCase implements SupplierUseCase<UserView> {

    private final DomainViewRepository repository;

    public Flux<UserView> get() {
        return repository.findAllUsers();
    }
}

package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.usecases.SupplierUseCase;
import co.com.sofkoin.beta.application.commons.views.UserDBView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class FindAllUsersUseCase implements SupplierUseCase<UserDBView> {

    private final DomainViewRepository repository;

    public Flux<UserDBView> get() {
        return repository.findAllUsers();
    }
}

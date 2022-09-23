package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.usecases.SupplierUseCase;
import co.com.sofkoin.beta.application.commons.usecases.UseCase;
import co.com.sofkoin.beta.application.commons.views.UserView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FindUserByIdUseCase implements UseCase<String, UserView> {

    private final DomainViewRepository repository;

    @Override
    public Mono<UserView> apply(Mono<String> userId) {
        return userId.flatMap(repository::findByUserId);
    }
}

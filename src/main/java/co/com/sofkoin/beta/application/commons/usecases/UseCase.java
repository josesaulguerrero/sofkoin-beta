package co.com.sofkoin.beta.application.commons.usecases;

import co.com.sofkoin.beta.application.commons.views.View;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface UseCase <T, R extends View> extends Function<Mono<T>, CorePublisher<R>> {
    @Override
    CorePublisher<R> apply(Mono<T> arg);
}

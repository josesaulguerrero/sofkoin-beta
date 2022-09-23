package co.com.sofkoin.beta.application.commons.usecases;

import reactor.core.CorePublisher;

import java.util.function.Supplier;

public interface SupplierUseCase <T> extends Supplier<CorePublisher<? extends T>> {
    @Override
    CorePublisher<? extends T> get();
}

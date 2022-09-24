package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.usecases.SupplierUseCase;
import co.com.sofkoin.beta.application.gateways.rest.RestClient;
import co.com.sofkoin.beta.application.gateways.rest.RestCryptoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GetCryptoPriceUseCase implements SupplierUseCase<RestCryptoResponse> {

    private RestClient restClient;

    @Override
    public Mono<RestCryptoResponse> get() {
        return restClient.getCryptoResponse();
    }

}

package co.com.sofkoin.beta.infrastructure.web.controllers;

import co.com.sofkoin.beta.application.gateways.rest.RestCryptoResponse;
import co.com.sofkoin.beta.application.usecases.GetCryptoPriceUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("view/cryptos")
public class CryptoViewController {

    private GetCryptoPriceUseCase getCryptoPriceUseCase;

    @GetMapping("/price")
    public ResponseEntity<Mono<RestCryptoResponse>> getCryptosPrice() {
        return new ResponseEntity<>(
                this.getCryptoPriceUseCase.get(),
                HttpStatus.OK
        );
    }

}

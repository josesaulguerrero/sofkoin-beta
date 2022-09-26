package co.com.sofkoin.beta.infrastructure.web.controllers;

import co.com.sofkoin.beta.application.usecases.GetCryptoPriceUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("view/cryptos")
public class CryptoViewController {

    private GetCryptoPriceUseCase getCryptoPriceUseCase;

    @GetMapping("/price")
    public ResponseEntity<Mono<List<Map<String, ? extends Serializable>>>> getCryptosPrice() {
        Mono<List<Map<String, ? extends Serializable>>> body = this.getCryptoPriceUseCase.get()
                .map(response ->
                        response.entrySet()
                                .stream()
                                .map(entry ->
                                        Map.of(
                                                "symbol", entry.getKey(),
                                                "price", entry.getValue().get("USD")
                                        )
                                )
                                .collect(Collectors.toList())
                );
        return new ResponseEntity<>(
                body,
                HttpStatus.OK
        );
    }

}

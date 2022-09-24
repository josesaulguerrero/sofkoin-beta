package co.com.sofkoin.beta.infrastructure.web.controllers;

import co.com.sofkoin.beta.application.commons.views.MarketView;
import co.com.sofkoin.beta.application.gateways.rest.RestCryptoResponse;
import co.com.sofkoin.beta.application.usecases.FindAllMarketsUseCase;
import co.com.sofkoin.beta.application.usecases.FindMarketByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("view/market")
public class MarketViewController {

    private FindMarketByIdUseCase findByMarketByIdUseCase;
    private FindAllMarketsUseCase findAllMarketsUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<Mono<MarketView>> getMarketById(@PathVariable String id) {
        return new ResponseEntity<>(
                this.findByMarketByIdUseCase.apply(Mono.just(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<Flux<MarketView>> getAllMarkets() {
        return new ResponseEntity<>(
                this.findAllMarketsUseCase.get(),
                HttpStatus.OK
        );
    }

}

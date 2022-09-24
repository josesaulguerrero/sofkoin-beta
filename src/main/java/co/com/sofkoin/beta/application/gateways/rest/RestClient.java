package co.com.sofkoin.beta.application.gateways.rest;

import reactor.core.publisher.Mono;

public interface RestClient {

    Mono<RestCryptoResponse> getCryptoResponse ();

}

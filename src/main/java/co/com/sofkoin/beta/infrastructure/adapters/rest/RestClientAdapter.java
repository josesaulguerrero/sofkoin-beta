package co.com.sofkoin.beta.infrastructure.adapters.rest;

import co.com.sofkoin.beta.application.gateways.rest.RestClient;
import co.com.sofkoin.beta.application.gateways.rest.RestCryptoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestClientAdapter implements RestClient {

    private static final String EXTERNAL_API_URL = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH,BNB,ADA,SOL,XRP,DOT,TRX,AVAX,ETC&tsyms=USD";

    private WebClient webClient;

    public RestClientAdapter() {
        this.webClient = WebClient.create();
    }

    @Override
    public Mono<RestCryptoResponse> getCryptoResponse() {
        return webClient.get()
                .uri(EXTERNAL_API_URL)
                .retrieve()
                .bodyToMono(RestCryptoResponse.class);
    }

}

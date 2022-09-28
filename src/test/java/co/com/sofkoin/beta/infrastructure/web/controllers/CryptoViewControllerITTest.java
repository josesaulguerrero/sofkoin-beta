package co.com.sofkoin.beta.infrastructure.web.controllers;

import co.com.sofkoin.beta.application.gateways.rest.RestCryptoResponse;
import co.com.sofkoin.beta.application.usecases.GetCryptoPriceUseCase;
import co.com.sofkoin.beta.infrastructure.adapters.rest.RestClientAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "MONGO_PASSWORD=password",
        "MONGO_USER=user",
        "RABBIT_HOST=host",
        "RABBIT_PASSWORD=rabbit_password",
        "RABBIT_PORT=5672",
        "RABBIT_USERNAME=adfassd",
        "RABBIT_VIRTUAL_HOST=asfdas"
})
class CryptoViewControllerITTest {
    @MockBean
    private RestClientAdapter restClientAdapter;

    @Autowired
    private GetCryptoPriceUseCase useCase;

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("#getCryptosPrice should return a list with the prices of the cryptos managed in the application.")
    public void getCryptosPrice_ShouldReturnAListWithThePricesOfTheAppCryptos_WhenSuccessful() {
        // Arrange
        RestCryptoResponse restResponse = new RestCryptoResponse();
        restResponse.put("BTC", Map.of("USD", 19600.0));
        restResponse.put("ETH", Map.of("USD", 22800.0));
        restResponse.put("SOL", Map.of("USD", 11450.0));
        BDDMockito
                .when(this.restClientAdapter.getCryptoResponse())
                .thenReturn(Mono.just(restResponse));

        // Act and Assert
        this.webClient.get()
                .uri("/view/cryptos/price")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                    .jsonPath("$.[0].symbol").isEqualTo("BTC")
                    .jsonPath("$.[0].price").isEqualTo(restResponse.get("BTC").get("USD"));

        BDDMockito.verify(this.restClientAdapter, BDDMockito.times(1))
                .getCryptoResponse();
    }
}
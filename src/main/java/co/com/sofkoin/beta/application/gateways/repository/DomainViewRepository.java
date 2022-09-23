package co.com.sofkoin.beta.application.gateways.repository;

import co.com.sofkoin.beta.application.gateways.repository.views.MarketView;
import co.com.sofkoin.beta.application.gateways.repository.views.UserView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DomainViewRepository {
    Flux<UserView> findAllUsers();
    Mono<UserView> findByUserId(String userId);
    Mono<UserView> findByUserEmail(String userId);
    Mono<UserView> saveUserView(UserView user);

    Flux<MarketView> findAllMarkets();
    Flux<MarketView> findMarketById(String marketId);
    Mono<MarketView> saveMarketView(MarketView market);
}

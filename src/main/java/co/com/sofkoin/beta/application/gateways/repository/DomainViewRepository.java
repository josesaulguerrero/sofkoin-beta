package co.com.sofkoin.beta.application.gateways.repository;

import co.com.sofkoin.beta.application.commons.views.MarketView;
import co.com.sofkoin.beta.application.commons.views.UserDBView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DomainViewRepository {
    Flux<UserDBView> findAllUsers();
    Mono<UserDBView> findByUserId(String userId);
    Mono<UserDBView> saveUserView(UserDBView user);

    Flux<MarketView> findAllMarkets();
    Mono<MarketView> findMarketById(String marketId);
    Mono<MarketView> saveMarketView(MarketView market);
}

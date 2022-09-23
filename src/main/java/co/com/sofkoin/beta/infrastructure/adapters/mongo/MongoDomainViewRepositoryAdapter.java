package co.com.sofkoin.beta.infrastructure.adapters.mongo;

import co.com.sofkoin.beta.application.commons.views.MarketView;
import co.com.sofkoin.beta.application.commons.views.UserView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class MongoDomainViewRepositoryAdapter implements DomainViewRepository {
    private static final String VIEWS_COLLECTION = "views";

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<UserView> findAllUsers() {
        return null;
    }

    @Override
    public Mono<UserView> findByUserId(String userId) {
        return null;
    }

    @Override
    public Mono<UserView> saveUserView(UserView user) {
        return this.mongoTemplate
                .save(user, VIEWS_COLLECTION);
    }

    @Override
    public Flux<MarketView> findAllMarkets() {
        return null;
    }

    @Override
    public Mono<MarketView> findMarketById(String marketId) {
        return null;
    }

    @Override
    public Mono<MarketView> saveMarketView(MarketView market) {
        return this.mongoTemplate
                .save(market, VIEWS_COLLECTION);
    }
}

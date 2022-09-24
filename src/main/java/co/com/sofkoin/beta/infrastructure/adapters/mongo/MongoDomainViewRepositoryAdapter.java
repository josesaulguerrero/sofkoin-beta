package co.com.sofkoin.beta.infrastructure.adapters.mongo;

import co.com.sofkoin.beta.application.commons.views.MarketView;
import co.com.sofkoin.beta.application.commons.views.UserDBView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class MongoDomainViewRepositoryAdapter implements DomainViewRepository {
    private static final String USERS_VIEWS_COLLECTION = "users_views";
    private static final String MARKET_VIEWS_COLLECTION = "markets_views";

    private final ReactiveMongoTemplate mongoTemplate;
    public Mono<UserDBView> saveUserView(UserDBView user) {
        return this.mongoTemplate
                .save(user, USERS_VIEWS_COLLECTION);
    }

    @Override
    public Mono<MarketView> saveMarketView(MarketView market) {
        return this.mongoTemplate
                .save(market, MARKET_VIEWS_COLLECTION);
    }

    @Override
    public Flux<UserDBView> findAllUsers() {
        return this.mongoTemplate.findAll(UserDBView.class, USERS_VIEWS_COLLECTION);
    }

    @Override
    public Mono<UserDBView> findByUserId(String userId) {
        return Mono.just(userId)
                .map(id -> new Query(Criteria.where("userId").is(id)))
                .flatMap(query -> this.mongoTemplate.findOne(query, UserDBView.class, USERS_VIEWS_COLLECTION));
    }

    @Override
    public Flux<MarketView> findAllMarkets() {
        return mongoTemplate.findAll(MarketView.class, MARKET_VIEWS_COLLECTION)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new IllegalStateException("Failed to return markets"))));
    }

    @Override
    public Mono<MarketView> findMarketById(String marketId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("marketId").is(marketId));
        return mongoTemplate.findOne(query, MarketView.class, MARKET_VIEWS_COLLECTION)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new IllegalArgumentException("Market id: " + marketId + " not found."))));
    }

}

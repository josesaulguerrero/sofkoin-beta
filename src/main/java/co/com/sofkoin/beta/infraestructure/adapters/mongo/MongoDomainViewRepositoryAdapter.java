package co.com.sofkoin.beta.infraestructure.adapters.mongo;

import co.com.sofkoin.beta.application.commons.views.MarketView;
import co.com.sofkoin.beta.application.commons.views.UserView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Repository
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
        return null;
    }

    @Override
    public Flux<MarketView> findAllMarkets() {
        return mongoTemplate.findAll(MarketView.class, VIEWS_COLLECTION)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new Throwable("Failed to return markets"))));
    }

    @Override
    public Mono<MarketView> findMarketById(String marketId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("marketId").is(marketId));
        return mongoTemplate.findOne(query, MarketView.class, VIEWS_COLLECTION)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new Throwable("Market id: " + marketId + " not found."))));
    }

    @Override
    public Mono<MarketView> saveMarketView(MarketView market) {
        return null;
    }

}

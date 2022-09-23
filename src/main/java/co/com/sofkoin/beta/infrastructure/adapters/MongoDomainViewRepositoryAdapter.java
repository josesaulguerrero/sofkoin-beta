package co.com.sofkoin.beta.infrastructure.adapters;

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

@Repository
@AllArgsConstructor
public class MongoDomainViewRepositoryAdapter implements DomainViewRepository {
  private final ReactiveMongoTemplate template;

  @Override
  public Flux<UserView> findAllUsers() {
    return template.findAll(UserView.class);
  }

  @Override
  public Mono<UserView> findByUserId(String userId) {
    return Mono.just(userId)
            .map(id -> new Query(Criteria.where("userId").is(id)))
            .flatMap(query -> template.findOne(query, UserView.class));
  }

  @Override
  public Mono<UserView> saveUserView(UserView user) {
    return null;
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
    return null;
  }
}

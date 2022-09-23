package co.com.sofkoin.beta.infrastructure.adapters;

import co.com.sofkoin.beta.application.gateways.bus.DomainViewBus;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import co.com.sofkoin.beta.application.gateways.updater.DomainUpdater;
import co.com.sofkoin.beta.domain.market.events.P2POfferPublished;
import co.com.sofkoin.beta.domain.user.events.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DomainViewUpdaterAdapter extends DomainUpdater {

  public DomainViewUpdaterAdapter(DomainViewRepository repository, DomainViewBus viewBus) {

    super.updater((UserSignedUp event) -> Mono.just(event));

    super.updater((P2POfferPublished event) -> Mono.just(event));

    super.updater((P2POfferPublished event) -> Mono.just(event));

    super.updater((OfferMessageSaved event) -> Mono.just(event));

    super.updater((MessageStatusChanged event) -> Mono.just(event));

    super.updater((TradeTransactionCommitted event) -> Mono.just(event));

    super.updater((P2PTransactionCommitted event) -> Mono.just(event));

    super.updater((WalletFunded event) -> Mono.just(event));

  }
}

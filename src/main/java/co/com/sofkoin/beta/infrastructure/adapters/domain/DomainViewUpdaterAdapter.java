package co.com.sofkoin.beta.infrastructure.adapters.domain;

import co.com.sofkoin.beta.application.commons.views.*;
import co.com.sofkoin.beta.application.gateways.bus.DomainViewBus;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import co.com.sofkoin.beta.application.gateways.updater.DomainUpdater;
import co.com.sofkoin.beta.domain.market.events.P2POfferPublished;
import co.com.sofkoin.beta.domain.user.events.*;
import co.com.sofkoin.beta.domain.user.values.ActivityTypes;
import co.com.sofkoin.beta.domain.user.values.MessageStatus;
import co.com.sofkoin.beta.domain.user.values.Timestamp;
import co.com.sofkoin.beta.domain.user.values.TransactionTypes;
import co.com.sofkoin.beta.domain.user.values.identities.ActivityID;
import co.com.sofkoin.beta.domain.user.values.identities.TransactionID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.Collections;

@Service
public class DomainViewUpdaterAdapter extends DomainUpdater {

    private final DomainViewRepository repository;
    private final DomainViewBus viewBus;

    public DomainViewUpdaterAdapter(DomainViewRepository repository, DomainViewBus viewBus) {
        this.repository = repository;
        this.viewBus = viewBus;

        super.addUpdater((UserSignedUp event) ->
                Mono.just(event)
                        .map(ev -> new UserDBView(
                                ev.getUserId(),
                                ev.getEmail(),
                                ev.getName() + " " + ev.getSurname(),
                                ev.getPhoneNumber(),
                                ev.getAvatarUrl(),
                                ev.getAuthMethod(),
                                0.0,
                                Collections.emptySet(),
                                Collections.emptySet(),
                                Collections.emptySet(),
                                Collections.emptySet()))
                        .flatMap(this.repository::saveUserView)
                        .subscribe());

        super.addUpdater((P2POfferPublished event) ->
                Mono.just(event)
                        .flatMap(ev ->
                                this.repository
                                        .findMarketById(ev.getMarketId())
                                        .flatMap(marketView -> {

                                            OfferView offerView = new OfferView(
                                                    ev.getOfferId(),
                                                    ev.getPublisherId(),
                                                    ev.getTargetAudienceId(),
                                                    ev.getCryptoSymbol(),
                                                    ev.getCryptoAmount(),
                                                    ev.getCryptoPrice());

                                            marketView.getOffers().add(offerView);

                                            addActivityToUser(ev.getPublisherId(), ActivityTypes.PUBLISH_OFFER.name());

                                            return Mono.just(marketView)
                                                    .flatMap(this.repository::saveMarketView)
                                                    .doOnNext(m -> viewBus.publishP2POfferCreatedEvent(offerView));
                                        })
                        ));

        super.addUpdater((P2PTransactionCommitted event) ->
                Mono.just(event)
                        .doOnNext(ev -> {
                            commitP2PTransaction(ev, ev.getBuyerId(), TransactionTypes.BUY);
                            commitP2PTransaction(ev, ev.getSellerId(), TransactionTypes.SELL);
                        }));

        super.addUpdater((OfferMessageSaved event) ->
                Mono.just(event)
                        .doOnNext(ev -> {
                            saveOfferMessage(ev, ev.getReceiverId());
                            saveOfferMessage(ev, ev.getSenderId());
                        }));

        super.addUpdater((MessageStatusChanged event) ->
                Mono.just(event)
                        .doOnNext(ev -> {
                            changeMessageStatus(ev, ev.getReceiverId());
                            changeMessageStatus(ev, ev.getSenderId());
                        }));

        super.addUpdater((TradeTransactionCommitted event) ->
                Mono.just(event)
                        .flatMap(ev ->
                                repository
                                        .findByUserId(ev.getBuyerId())
                                        .map(userView -> {
                                            TransactionView transactionView = new TransactionView(
                                                    ev.getTransactionId(),
                                                    ev.getTransactionType(),
                                                    ev.getCryptoSymbol(),
                                                    ev.getCryptoAmount(),
                                                    ev.getCryptoPrice(),
                                                    Timestamp.from(ev.getTimestamp()).value()
                                            );

                                            CryptoView cryptoView =
                                                    userView.getCrypto(ev.getCryptoSymbol());

                                            if (ev.getTransactionType().equals(TransactionTypes.BUY.name())) {
                                                userView.decreaseCashBalance(ev.getCash());
                                                cryptoView.increaseCryptoAmount(ev.getCryptoAmount());
                                            } else {
                                                cryptoView.decreaseCryptoAmount(ev.getCryptoAmount());
                                                userView.increaseCashBalance(ev.getCash());
                                            }


                                            userView.getTransactions().add(transactionView);

                                            return userView;
                                        })
                                        .flatMap(repository::saveUserView)));


        super.addUpdater((WalletFunded event) ->
                Mono.just(event)
                        .flatMap(ev ->
                                repository
                                        .findByUserId(ev.getUserId())
                                        .map(userView -> {

                                            TransactionView transactionView = new TransactionView(
                                                    new TransactionID().value(),
                                                    TransactionTypes.FUND.name(),
                                                    "USD",
                                                    ev.getCashAmount(),
                                                    ev.getCashAmount(),
                                                    Timestamp.from(ev.getTimestamp()).value()
                                            );

                                            userView.increaseCashBalance(ev.getCashAmount());

                                            userView.getTransactions().add(transactionView);
                                            return userView;
                                        }))
                        .flatMap(repository::saveUserView)
        );

        super.addUpdater((UserLoggedIn event) -> addActivityToUser(event.getUserId(), ActivityTypes.LOGIN.name()));

        super.addUpdater((UserLoggedOut event) -> addActivityToUser(event.getUserId(), ActivityTypes.LOGOUT.name()));

    }

    private void addActivityToUser(String userId, String activityType){
        repository.findByUserId(userId)
                .map(publisher -> {
                    publisher.getActivities().add(new ActivityView(
                            new ActivityID().value(),
                            new Timestamp().value(),
                            activityType
                    ));
                    return publisher;
                }).flatMap(this.repository::saveUserView)
                .subscribe();
    }

    private void commitP2PTransaction(P2PTransactionCommitted ev, String userId, TransactionTypes transactionType) {
        repository
                .findByUserId(userId)
                .map(user -> {
                    TransactionView transactionView = new TransactionView(
                            ev.getTransactionId(),
                            ev.getTransactionType(),
                            ev.getCryptoSymbol(),
                            ev.getCryptoAmount(),
                            ev.getCryptoPrice(),
                            Timestamp.from(ev.getTimestamp()).value());

                    user.getTransactions().add(transactionView);

                    CryptoView cryptoView =
                            user.getCrypto(ev.getCryptoSymbol());

                    if (transactionType == TransactionTypes.BUY) {
                        user.decreaseCashBalance(ev.getCash());
                        cryptoView.increaseCryptoAmount(ev.getCryptoAmount());
                    } else {
                        user.increaseCashBalance(ev.getCash());
                        cryptoView.decreaseCryptoAmount(ev.getCryptoAmount());
                    }

                    return
                            Mono.just(user)
                                    .flatMap(repository::saveUserView)
                                    .doOnNext(u -> viewBus.publishP2PTransactionCommittedEvent(transactionView));
                });
    }

    private void changeMessageStatus(MessageStatusChanged ev, String userId) {
        repository
                .findByUserId(userId)
                .map(user -> {
                    MessageView message =
                            user.getMessages().stream()
                                    .filter(messageView -> messageView.getMessageId().equals(ev.getMessageId()))
                                    .findFirst().get();

                    message.setStatus(ev.getNewStatus());

                    return Mono.just(user)
                            .flatMap(repository::saveUserView)
                            .doOnNext(u -> viewBus.publishMessageStatusChangedEvent(message));
                });
    }


    private void saveOfferMessage(OfferMessageSaved event, String id) {
        repository
                .findByUserId(id)
                .map(user -> {
                    MessageView messageView = new MessageView(
                            event.getMessageId(),
                            MessageStatus.PENDING.name(),
                            event.getSenderId(),
                            event.getMarketId(),
                            event.getReceiverId(),
                            event.getCryptoAmount(),
                            event.getCryptoPrice(),
                            event.getCryptoSymbol()
                    );
                    user.getMessages().add(messageView);
                    return
                            Mono.just(user)
                                    .flatMap(repository::saveUserView)
                                    .doOnNext(u -> viewBus.publishMessageSavedEvent(messageView));
                });
    }

}

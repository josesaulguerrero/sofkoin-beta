package co.com.sofkoin.beta.application.gateways.bus;

import co.com.sofkoin.beta.application.commons.views.MessageView;
import co.com.sofkoin.beta.application.commons.views.OfferView;
import co.com.sofkoin.beta.application.commons.views.TransactionView;

public interface DomainViewBus {
    // offers
    void publishP2POfferCreatedEvent(OfferView offerView);
    void publishP2POfferDeletedEvent(OfferView offerView);

    // messages
    void publishMessageSavedEvent(MessageView messageView);
    void publishMessageStatusChangedEvent(MessageView messageView);

    // transactions
    void publishP2PTransactionCommittedEvent(TransactionView transactionView);
}

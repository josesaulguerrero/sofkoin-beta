package co.com.sofkoin.beta.infrastructure.adapters.rabbitmq;

import co.com.sofkoin.beta.application.commons.views.MessageView;
import co.com.sofkoin.beta.application.commons.views.OfferView;
import co.com.sofkoin.beta.application.commons.views.TransactionView;
import co.com.sofkoin.beta.application.commons.views.View;
import co.com.sofkoin.beta.application.gateways.bus.DomainViewBus;
import co.com.sofkoin.beta.infrastructure.commons.json.JSONMapper;
import co.com.sofkoin.beta.infrastructure.config.rabbitmq.RabbitMQQueuesConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMQDomainViewBusAdapter implements DomainViewBus {
    private final JSONMapper jsonMapper;
    private final RabbitTemplate rabbitTemplate;

    private void publishView(View view, String routingKey) {
        this.rabbitTemplate.convertAndSend(
                RabbitMQQueuesConfiguration.EXCHANGE,
                routingKey,
                this.jsonMapper.writeToJson(view)
        );
    }

    @Override
    public void publishP2POfferCreatedEvent(OfferView offerView) {
        this.publishView(
                offerView,
                RabbitMQQueuesConfiguration.PROXY_ROUTING_KEY_P2P_OFFER_CREATED
        );
    }

    @Override
    public void publishP2POfferDeletedEvent(OfferView offerView) {
        this.publishView(
                offerView,
                RabbitMQQueuesConfiguration.PROXY_ROUTING_KEY_P2P_OFFER_DELETED
        );
    }

    @Override
    public void publishMessageSavedEvent(MessageView messageView) {
        this.publishView(
                messageView,
                RabbitMQQueuesConfiguration.PROXY_ROUTING_KEY_MESSAGE_SAVED
        );
    }

    @Override
    public void publishMessageStatusChangedEvent(MessageView messageView) {
        this.publishView(
                messageView,
                RabbitMQQueuesConfiguration.PROXY_ROUTING_KEY_MESSAGE_STATUS_CHANGED
        );
    }

    @Override
    public void publishP2PTransactionCommittedEvent(TransactionView transactionView) {
        this.publishView(
                transactionView,
                RabbitMQQueuesConfiguration.PROXY_ROUTING_KEY_TRANSACTION_COMMITTED
        );
    }
}

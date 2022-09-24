package co.com.sofkoin.beta.infrastructure.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQQueuesConfiguration {
    public static final String EXCHANGE = "exchange.main";

    // ###  queues' names ###
    public static final String PROXY_QUEUE_P2P_OFFER_CREATED = "domain_views.p2p_offer_created";
    public static final String PROXY_QUEUE_P2P_OFFER_DELETED = "domain_views.p2p_offer_deleted";
    public static final String PROXY_QUEUE_MESSAGE_SAVED = "domain_views.message_saved";
    public static final String PROXY_QUEUE_MESSAGE_STATUS_CHANGED = "domain_views.message_status_changed";
    public static final String PROXY_QUEUE_P2P_TRANSACTION_COMMITTED = "domain_views.p2p_transaction_committed";

    // ###  routing keys ###
    public static final String PROXY_ROUTING_KEY_P2P_OFFER_CREATED = "routing_key.p2p_offer_created";
    public static final String PROXY_ROUTING_KEY_P2P_OFFER_DELETED = "routing_key.p2p_offer_deleted";
    public static final String PROXY_ROUTING_KEY_MESSAGE_SAVED = "routing_key.message_saved";
    public static final String PROXY_ROUTING_KEY_MESSAGE_STATUS_CHANGED = "routing_key.message_status_changed";
    public static final String PROXY_ROUTING_KEY_TRANSACTION_COMMITTED = "routing_key.p2p_transaction_committed";

    @Bean
    public TopicExchange getTopicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue offerCreatedQueue() {
        return new Queue(PROXY_QUEUE_P2P_OFFER_CREATED);
    }

    @Bean
    public Queue offerDeletedQueue() {
        return new Queue(PROXY_QUEUE_P2P_OFFER_DELETED);
    }

    @Bean
    public Queue messageSavedQueue() {
        return new Queue(PROXY_QUEUE_MESSAGE_SAVED);
    }

    @Bean
    public Queue messagedStatusChangedQueue() {
        return new Queue(PROXY_QUEUE_MESSAGE_STATUS_CHANGED);
    }

    @Bean
    public Queue transactionCommittedQueue() {
        return new Queue(PROXY_QUEUE_P2P_TRANSACTION_COMMITTED);
    }

    @Bean
    public Binding bindingToOfferCreatedEventQueue() {
        return BindingBuilder
                .bind(this.offerCreatedQueue())
                .to(getTopicExchange())
                .with(PROXY_ROUTING_KEY_P2P_OFFER_CREATED);
    }

    @Bean
    public Binding bindingToOfferDeletedEventQueue() {
        return BindingBuilder
                .bind(this.offerDeletedQueue())
                .to(getTopicExchange())
                .with(PROXY_ROUTING_KEY_P2P_OFFER_DELETED);
    }

    @Bean
    public Binding bindingToMessageSavedEventQueue() {
        return BindingBuilder
                .bind(this.messageSavedQueue())
                .to(getTopicExchange())
                .with(PROXY_ROUTING_KEY_MESSAGE_SAVED);
    }

    @Bean
    public Binding bindingToMessageStatusChangedEventQueue() {
        return BindingBuilder
                .bind(this.messagedStatusChangedQueue())
                .to(getTopicExchange())
                .with(PROXY_ROUTING_KEY_MESSAGE_STATUS_CHANGED);
    }

    @Bean
    public Binding bindingToTransactionCommittedEventQueue() {
        return BindingBuilder
                .bind(this.transactionCommittedQueue())
                .to(getTopicExchange())
                .with(PROXY_ROUTING_KEY_TRANSACTION_COMMITTED);
    }
}

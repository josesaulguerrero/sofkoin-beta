package co.com.sofkoin.beta.infrastructure.config.rabbitmq;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofkoin.beta.application.gateways.updater.DomainUpdater;
import co.com.sofkoin.beta.infrastructure.adapters.rabbitmq.RabbitMQMessage;
import co.com.sofkoin.beta.infrastructure.commons.json.JSONMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Slf4j
public class RabbitMQListenerConfiguration {
    public static final String ALPHA_EVENTS_QUEUE = "domain_events.main";
    private final DomainUpdater domainUpdater;
    private final JSONMapper jsonMapper;

    @RabbitListener(queues = {ALPHA_EVENTS_QUEUE})
    public void alphaDomainEventListener(String data) {
        log.info(data);
        try {

            RabbitMQMessage message = (RabbitMQMessage) this.jsonMapper.readFromJson(data, RabbitMQMessage.class);
            String eventClassPath = message.getType().replace("alpha", "beta");
            var event = (DomainEvent) this.jsonMapper.readFromJson(message.getBody(), Class.forName(eventClassPath));
            this.domainUpdater.applyEvent(event);

        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }

    }
}

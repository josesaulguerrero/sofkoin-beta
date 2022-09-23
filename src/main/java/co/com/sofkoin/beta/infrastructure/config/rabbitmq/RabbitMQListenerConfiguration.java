package co.com.sofkoin.beta.infrastructure.config.rabbitmq;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofkoin.beta.infrastructure.commons.json.JSONMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RabbitMQListenerConfiguration {
    public static final String ALPHA_EVENTS_QUEUE = "domain_events.main";
    // private final DomainUpdater domainUpdater;
    private final JSONMapper jsonMapper;

    @RabbitListener(queues = {ALPHA_EVENTS_QUEUE})
    public void alphaDomainEventListener(String data) {
        DomainEvent event = (DomainEvent) this.jsonMapper.readFromJson(data, DomainEvent.class);
        // this.domainUpdater.apply(event);
        // TODO create a domain updater interface.
    }
}

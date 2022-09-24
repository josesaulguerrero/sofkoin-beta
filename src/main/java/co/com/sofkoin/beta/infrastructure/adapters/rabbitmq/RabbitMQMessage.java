package co.com.sofkoin.beta.infrastructure.adapters.rabbitmq;

import co.com.sofkoin.beta.infrastructure.commons.json.JSONMapper;
import co.com.sofkoin.beta.infrastructure.commons.json.JSONMapperImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMQMessage {
    private static final JSONMapper jsonMapper = new JSONMapperImpl();
    private String type;
    private String body;
    private Instant instant;


    public RabbitMQMessage(String type, String body) {
        this.type = type;
        this.body = body;
        this.instant = Instant.now();
    }

    public String serialize() {
        return jsonMapper.writeToJson(this);
    }

    public static RabbitMQMessage from(String charSequence) {
        return RabbitMQMessage.deserialize(charSequence);
    }

    public static RabbitMQMessage deserialize(String charSequence) {
        return (RabbitMQMessage) jsonMapper.readFromJson(charSequence, RabbitMQMessage.class);
    }
}

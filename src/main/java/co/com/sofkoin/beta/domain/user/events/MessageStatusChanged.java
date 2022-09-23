package co.com.sofkoin.beta.domain.user.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageStatusChanged extends DomainEvent {

    private String receiverId;
    private String senderId;
    private String messageId;
    private String newStatus;

    public MessageStatusChanged() {
        super(MessageStatusChanged.class.getName());
    }

    public MessageStatusChanged(String receiverId, String senderId,String messageId, String newStatus) {
        super(MessageStatusChanged.class.getName());
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.messageId = messageId;
        this.newStatus = newStatus;
    }

}

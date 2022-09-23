package co.com.sofkoin.beta.domain.user.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoggedOut extends DomainEvent {
    private String userId;

    public UserLoggedOut() {
        super(UserLoggedOut.class.getName());
    }

    public UserLoggedOut(String userId) {
        super(UserLoggedOut.class.getName());
        this.userId = userId;
    }
}

package co.com.sofkoin.beta.domain.market.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P2POfferDeleted extends DomainEvent {

    private String marketId;
    private String offerId;

    public P2POfferDeleted() {
        super(P2POfferDeleted.class.getName());
    }

    public P2POfferDeleted(String offerId, String marketId) {
        super(P2POfferDeleted.class.getName());
        this.marketId = marketId;
        this.offerId = offerId;
    }
}

package co.com.sofkoin.beta.domain.market.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P2POfferPublished extends DomainEvent {
    private String offerId;
    private String marketId;
    private String publisherId;
    private String cryptoSymbol;
    private Double cryptoAmount;
    private Double cryptoPrice;

    private String targetAudienceId;

    public P2POfferPublished() {
        super(P2POfferPublished.class.getName());
    }

    public P2POfferPublished(String offerId,
                             String marketId,
                             String publisherId,
                             String cryptoSymbol,
                             Double cryptoAmount,
                             Double cryptoPrice,
                             String targetAudienceId)
    {
        super(P2POfferPublished.class.getName());
        this.offerId = offerId;
        this.marketId = marketId;
        this.publisherId = publisherId;
        this.cryptoSymbol = cryptoSymbol;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.targetAudienceId = targetAudienceId;
    }
}

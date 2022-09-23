package co.com.sofkoin.beta.domain.market.entities.root;

import co.com.sofka.domain.generic.EventChange;
import co.com.sofkoin.beta.domain.common.values.CryptoSymbol;
import co.com.sofkoin.beta.domain.common.values.identities.UserID;
import co.com.sofkoin.beta.domain.market.entities.Offer;
import co.com.sofkoin.beta.domain.market.events.MarketCreated;
import co.com.sofkoin.beta.domain.market.events.P2POfferDeleted;
import co.com.sofkoin.beta.domain.market.events.P2POfferPublished;
import co.com.sofkoin.beta.domain.market.values.Country;
import co.com.sofkoin.beta.domain.market.values.OfferCryptoAmount;
import co.com.sofkoin.beta.domain.market.values.OfferCryptoPrice;
import co.com.sofkoin.beta.domain.market.values.identities.OfferId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MarketEventListener extends EventChange {

    public MarketEventListener(Market market){
        super.apply((MarketCreated event) -> {
            market.country = new Country(event.getCountry());
            market.offers = new HashSet<>();
            market.cryptoSymbols = new HashSet<>();
            market.cryptoSymbols.addAll(Set.of(new CryptoSymbol("XRP"), new CryptoSymbol("BTC")));

        });

        super.apply((P2POfferPublished event) -> {
            Offer offer = new Offer(new OfferId(event.getOfferId()),
                    new UserID(event.getPublisherId()),
                    new CryptoSymbol(event.getCryptoSymbol()),
                    new OfferCryptoAmount(event.getCryptoAmount()),
                    new OfferCryptoPrice(event.getCryptoPrice()),
                    new UserID(event.getTargetAudienceId()));
            market.offers.add(offer);
        });

        super.apply((P2POfferDeleted event) -> {
            Offer offerToDelete  = market.offers
                    .stream()
                    .filter(offer -> Objects.equals(offer.identity().value(), event.getOfferId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("The given id does not belong to any active offer."));
            market.offers.remove(offerToDelete);
        });

    }

}

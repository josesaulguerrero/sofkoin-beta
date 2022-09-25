package co.com.sofkoin.beta.application.commons.views;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MarketView extends View {
    @Id
    private String marketId;
    private Set<OfferView> offers;
    private Set<String> cryptoSymbols;

    public void deleteP2POfferById(String offerId) {
        this.offers.removeIf(view -> view.getOfferId().equals(offerId));
    }

    public OfferView findOfferById(String offerId) {
        return this.offers.stream()
                .filter(offerView -> offerView.getOfferId().equals(offerId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The offer with the given id does not exist."));
    }
}

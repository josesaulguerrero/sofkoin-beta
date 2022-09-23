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
}

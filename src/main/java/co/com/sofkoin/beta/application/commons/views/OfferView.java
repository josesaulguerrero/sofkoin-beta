package co.com.sofkoin.beta.application.commons.views;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OfferView {
    private String offerId;
    private String publisherId;
    private String targetAudienceId;
    private String cryptoSymbol;
    private Double cryptoAmount;
    private Double cryptoPrice;
}

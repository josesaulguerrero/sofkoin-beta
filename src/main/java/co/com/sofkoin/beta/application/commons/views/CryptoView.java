package co.com.sofkoin.beta.application.commons.views;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CryptoView extends View {

    protected String symbol;
    protected Double amount;

    public void increaseCryptoAmount(Double cryptoAmount) {
        this.amount += cryptoAmount;
    }

    public void decreaseCryptoAmount(Double cryptoAmount) {
        this.amount -= cryptoAmount;
    }
}

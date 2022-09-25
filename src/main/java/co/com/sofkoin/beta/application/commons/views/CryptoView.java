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
        this.amount = Double.sum(cryptoAmount, this.amount);
    }

    public void decreaseCryptoAmount(Double cryptoAmount) {
        this.amount = this.amount - cryptoAmount;
    }
}

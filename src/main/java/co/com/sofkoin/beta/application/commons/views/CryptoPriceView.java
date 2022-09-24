package co.com.sofkoin.beta.application.commons.views;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CryptoPriceView extends CryptoView{
    private Double priceUsd;

    public CryptoPriceView(String symbol, Double amount, Double priceUsd) {
        super(symbol, amount);
        this.priceUsd = priceUsd;
    }

    public static Double calculateCurrenPrice(Double amount, Double price){
       return price * amount;
    }

}

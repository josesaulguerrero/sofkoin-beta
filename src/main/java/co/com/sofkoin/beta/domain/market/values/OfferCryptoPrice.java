package co.com.sofkoin.beta.domain.market.values;

import co.com.sofka.domain.generic.ValueObject;

public class OfferCryptoPrice implements ValueObject<Double> {

    private final Double value;

    public OfferCryptoPrice(Double value) {
        if (value < 0.0) {
            throw new IllegalArgumentException("Offer crypto price cannot be negative.");
        }
        this.value = value;
    }

    @Override
    public Double value() {
        return this.value;
    }

}

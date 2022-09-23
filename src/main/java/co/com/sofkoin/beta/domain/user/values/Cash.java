package co.com.sofkoin.beta.domain.user.values;

import co.com.sofka.domain.generic.ValueObject;

public class Cash implements ValueObject<Double> {
    private final Double value;

    public Cash(Double value) {
        if (value < 0.0) {
            throw new IllegalArgumentException("Invalid Value(Balance must be positive).");
        }

        this.value = value;
    }

    public Double value() {
        return value;
    }
}

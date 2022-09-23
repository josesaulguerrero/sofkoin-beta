package co.com.sofkoin.beta.application.gateways.repository.views;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CryptoView {
    private String symbol;
    private Double amount;
}

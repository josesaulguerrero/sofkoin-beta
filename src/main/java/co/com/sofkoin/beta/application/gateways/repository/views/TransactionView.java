package co.com.sofkoin.beta.application.gateways.repository.views;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionView {
    private String transactionId;
    private String type;
    private String CryptoSymbol;
    private Double cryptoAmount;
    private Double cryptoPrice;
    private LocalDateTime timestamp;
}

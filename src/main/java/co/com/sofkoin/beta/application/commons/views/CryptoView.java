package co.com.sofkoin.beta.application.commons.views;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CryptoView extends View {
    private String symbol;
    private Double amount;
}

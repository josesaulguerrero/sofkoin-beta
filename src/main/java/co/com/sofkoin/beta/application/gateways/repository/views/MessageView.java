package co.com.sofkoin.beta.application.gateways.repository.views;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageView {
    private String messageId;
    private String status;
    private String senderId;
    private String marketId;
    private String receiverId;
    private Double proposalCryptoAmount;
    private Double proposalCryptoPrice;
    private String proposalCryptoSymbol;
}

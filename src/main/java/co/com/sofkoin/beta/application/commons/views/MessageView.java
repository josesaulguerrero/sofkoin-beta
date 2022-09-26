package co.com.sofkoin.beta.application.commons.views;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageView extends View {
    private String messageId;
    private String status;
    private String senderId;
    private String marketId;
    private String receiverId;
    private String messageRelationType;
    private Double proposalCryptoAmount;
    private Double proposalCryptoPrice;
    private String proposalCryptoSymbol;
}

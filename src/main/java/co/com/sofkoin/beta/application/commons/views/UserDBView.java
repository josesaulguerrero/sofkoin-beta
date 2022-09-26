package co.com.sofkoin.beta.application.commons.views;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDBView extends View {
    @Id
    private String userId;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String avatarUrl;
    private String authMethod;
    private Double currentCash;
    private Set<MessageView> messages;
    private Set<CryptoView> cryptos;
    private Set<ActivityView> activities;
    private Set<TransactionView> transactions;


    public void increaseCashBalance(Double cash) {
        this.currentCash = Double.sum(cash, this.currentCash);
    }

    public void decreaseCashBalance(Double cash) {
        this.currentCash = this.currentCash - cash;
    }

    public CryptoView getCrypto(String cryptoSymbol) {
        return this.getCryptos().stream()
                .filter(crypto -> crypto.getSymbol().equals(cryptoSymbol))
                .findFirst()
                .orElseGet(() -> {
                    CryptoView cryptoView = new CryptoView(cryptoSymbol, 0.0);
                    this.cryptos.add(cryptoView);
                    return cryptoView;
                });
    }

    public void addTransaction(TransactionView transactionView) {
        this.transactions.add(transactionView);
    }

    public void addMessage(MessageView messageView) {
        this.messages.add(messageView);
    }
}

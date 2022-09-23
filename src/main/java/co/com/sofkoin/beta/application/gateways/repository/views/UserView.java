package co.com.sofkoin.beta.application.gateways.repository.views;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserView {
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
}

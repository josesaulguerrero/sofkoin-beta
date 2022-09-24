package co.com.sofkoin.beta.application.commons.views;

import lombok.AllArgsConstructor;
import java.util.Set;

@AllArgsConstructor
public class UserDashboardView extends View{

    private String userId;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String avatarUrl;
    private Double currentCash;
    private Set<MessageView> messages;
    private Set<CryptoPriceView> cryptos;
    private Set<ActivityView> activities;
    private Set<TransactionView> transactions;

}

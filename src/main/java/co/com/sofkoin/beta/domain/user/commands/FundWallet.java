package co.com.sofkoin.beta.domain.user.commands;

import co.com.sofka.domain.generic.Command;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FundWallet extends Command {
    private String userId;
    private Double cashAmount;
}

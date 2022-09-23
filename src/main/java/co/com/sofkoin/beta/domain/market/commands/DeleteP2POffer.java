package co.com.sofkoin.beta.domain.market.commands;

import co.com.sofka.domain.generic.Command;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeleteP2POffer extends Command {
    private String marketId;
    private String offerId;
}

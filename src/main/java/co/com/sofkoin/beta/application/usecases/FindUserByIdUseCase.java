package co.com.sofkoin.beta.application.usecases;

import co.com.sofkoin.beta.application.commons.usecases.UseCase;
import co.com.sofkoin.beta.application.commons.views.CryptoPriceView;
import co.com.sofkoin.beta.application.commons.views.UserDashboardView;
import co.com.sofkoin.beta.application.gateways.repository.DomainViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class FindUserByIdUseCase implements UseCase<String, UserDashboardView> {

    private final DomainViewRepository repository;
    private final GetCryptoPriceUseCase getCryptoPriceUseCase;

    @Override
    public Mono<UserDashboardView> apply(Mono<String> userId) {

        return userId.flatMap(repository::findByUserId)
                .flatMap(user ->
                        getCryptoPriceUseCase.get()
                                .map(cryptoResponse -> {

                                    Set<CryptoPriceView> cryptoPriceViews = new HashSet<>();

                                    user.getCryptos().forEach(cryptoView -> {
                                        var cryptoAmount = cryptoView.getAmount();
                                        var cryptoSymbol = cryptoView.getSymbol();

                                        Double pricePerCrypto = cryptoResponse.get(cryptoSymbol).get("USD");
                                        cryptoPriceViews.add(new CryptoPriceView(cryptoSymbol,
                                                cryptoAmount,
                                                CryptoPriceView.calculateCurrenPrice(cryptoAmount, pricePerCrypto)));
                                    });

                                    return new UserDashboardView(
                                            user.getUserId(),
                                            user.getEmail(),
                                            user.getFullName(),
                                            user.getPhoneNumber(),
                                            user.getAvatarUrl(),
                                            user.getCurrentCash(),
                                            user.getMessages(),
                                            cryptoPriceViews,
                                            user.getActivities(),
                                            user.getTransactions());

                                })
                );
    }

}

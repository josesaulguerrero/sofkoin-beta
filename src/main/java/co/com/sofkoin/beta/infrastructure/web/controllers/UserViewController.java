package co.com.sofkoin.beta.infrastructure.web.controllers;

import co.com.sofkoin.beta.application.commons.views.UserDBView;
import co.com.sofkoin.beta.application.commons.views.UserDashboardView;
import co.com.sofkoin.beta.application.usecases.FindAllUsersUseCase;
import co.com.sofkoin.beta.application.usecases.FindUserByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("view/user")
public class UserViewController {

    private FindUserByIdUseCase findUserByIdUseCase;
    private FindAllUsersUseCase findAllUsersUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<Mono<UserDashboardView>> findUserById(@PathVariable String id) {
        return new ResponseEntity<>(
                this.findUserByIdUseCase.apply(Mono.just(id)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<Flux<UserDBView>> findAllUsers() {
        return new ResponseEntity<>(
                this.findAllUsersUseCase.get(),
                HttpStatus.CREATED
        );
    }

}

package org.kelava.bookstore.loyalty.domain.loyalty.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.loyalty.domain.loyalty.core.LoyaltyUseCase;

@Component
@RequiredArgsConstructor
public class LoyaltyHttpHandler {

    private final LoyaltyUseCase loyaltyUseCase;

    public Mono<ServerResponse> get(final ServerRequest serverRequest) {
        return ServerResponse.ok().body(this.loyaltyUseCase.getLoyaltyByCustomerId(serverRequest.pathVariable("id")), LoyaltyResponse.class);
    }
}

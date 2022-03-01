package org.kelava.bookstore.loyalty.domain.loyalty.core;

import io.lettuce.core.TransactionResult;
import org.kelava.bookstore.loyalty.domain.loyalty.application.command.NewPurchaseCommand;
import reactor.core.publisher.Mono;

public interface LoyaltyUseCase {
    Mono<TransactionResult> incrementLoyaltyPoints(NewPurchaseCommand newPurchaseCommand);

    Mono<Loyalty> getLoyaltyByCustomerId(String customerId);
}

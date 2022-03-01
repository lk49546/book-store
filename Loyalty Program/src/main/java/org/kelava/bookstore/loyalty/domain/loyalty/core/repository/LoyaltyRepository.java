package org.kelava.bookstore.loyalty.domain.loyalty.core.repository;

import io.lettuce.core.TransactionResult;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.loyalty.domain.loyalty.core.Loyalty;
import org.kelava.bookstore.loyalty.domain.loyalty.core.repository.command.IncrementCountCommand;

public interface LoyaltyRepository {

    Mono<TransactionResult> incrementCount(IncrementCountCommand incrementCountCommand);
    Mono<Loyalty> getLoyalty(String customerId);
}

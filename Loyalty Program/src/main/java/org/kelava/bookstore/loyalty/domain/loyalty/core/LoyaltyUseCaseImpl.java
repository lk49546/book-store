package org.kelava.bookstore.loyalty.domain.loyalty.core;

import io.lettuce.core.TransactionResult;
import lombok.RequiredArgsConstructor;
import org.kelava.bookstore.loyalty.domain.loyalty.application.command.NewPurchaseCommand;
import org.kelava.bookstore.loyalty.domain.loyalty.core.repository.LoyaltyRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.loyalty.domain.loyalty.core.repository.command.IncrementCountCommand;

@Service
@RequiredArgsConstructor
public class LoyaltyUseCaseImpl implements LoyaltyUseCase {

    private final LoyaltyRepository loyaltyRepository;

    @Override
    public Mono<TransactionResult> incrementLoyaltyPoints(final NewPurchaseCommand newPurchaseCommand) {
        return this.loyaltyRepository.incrementCount(new IncrementCountCommand(newPurchaseCommand.customerId()));
    }

    @Override
    public Mono<Loyalty> getLoyaltyByCustomerId(final String customerId) {
        return this.loyaltyRepository.getLoyalty(customerId);
    }

}

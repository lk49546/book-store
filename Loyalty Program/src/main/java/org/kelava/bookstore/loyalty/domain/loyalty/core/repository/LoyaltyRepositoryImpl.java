package org.kelava.bookstore.loyalty.domain.loyalty.core.repository;

import io.lettuce.core.TransactionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.loyalty.domain.loyalty.core.Loyalty;
import org.kelava.bookstore.loyalty.domain.loyalty.core.repository.command.IncrementCountCommand;
import org.kelava.bookstore.loyalty.domain.loyalty.infrastructure.redis.RedisLoyaltyRepository;

import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class LoyaltyRepositoryImpl implements LoyaltyRepository {

    private final RedisLoyaltyRepository redisLoyaltyRepository;

    @Override
    public Mono<TransactionResult> incrementCount(final IncrementCountCommand incrementCountCommand) {
        return this.redisLoyaltyRepository.increment(incrementCountCommand.customerId());
    }

    @Override
    public Mono<Loyalty> getLoyalty(final String customerId) {
        return this.redisLoyaltyRepository.findCountByCustomerId(customerId).map(loyaltyMapper(customerId));
    }


    protected static Function<Integer, Loyalty> loyaltyMapper(final String customerId) {
        return counter -> new Loyalty(customerId, counter);
    }
}

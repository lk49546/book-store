package org.kelava.bookstore.loyalty.domain.loyalty.infrastructure.redis;

import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RedisLoyaltyRepository {

    private final RedisReactiveCommands<String, String> redisReactiveCommands;

    public Mono<Integer> findCountByCustomerId(final String id) {
        return this.redisReactiveCommands.get(id).defaultIfEmpty("0").map(Integer::parseInt);
    }

    //workaround to have a proper transactional behaviour
    public Mono<TransactionResult> increment(final String customerId) {
        return this.redisReactiveCommands.multi().flatMap(ignore -> {
            redisReactiveCommands.get(customerId).defaultIfEmpty("0").flatMap(valStr -> {
                final Integer val = Integer.parseInt(valStr);
                if (val == 10) {
                    return redisReactiveCommands.set(customerId, "0");
                } else {
                    return redisReactiveCommands.set(customerId, String.valueOf(val + 1));
                }
            }).subscribe();
            return redisReactiveCommands.exec();
        });
    }

}

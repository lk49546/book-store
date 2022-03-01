package org.kelava.bookstore.loyalty.infrastructure;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public RedisReactiveCommands<String, String> redisReactiveCommands() {
        return RedisClient.create(RedisURI.Builder.redis("redis", 6379).build()).connect().reactive();
    }
}

package org.kelava.bookstore.purchase.domain.order.core.port.outgoing;

import org.kelava.bookstore.purchase.domain.order.core.dto.LoyaltyResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "loyalty", url = "http://loyalty:8000")
public interface GetLoyaltyPoints {

    @GetMapping("/api/v1/loyalty/{customerId}")
    Mono<LoyaltyResponse> findLoyaltyByCustomerId(@PathVariable("customerId") String customerId);
}
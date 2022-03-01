package org.kelava.bookstore.purchase.domain.order.core;

import reactor.core.publisher.Mono;

public interface DiscountProcessor {

    Mono<Order> process(Mono<Order> order);
}

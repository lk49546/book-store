package org.kelava.bookstore.purchase.domain.order.core.port.outgoing;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.purchase.domain.order.core.Order;

public interface OrderDatabase {

    Mono<Order> save(Order order);
    Mono<Order> findById(String id);

    Flux<Order> findAll();
}

package org.kelava.bookstore.purchase.domain.order.core.port.incoming;

import org.kelava.bookstore.purchase.domain.order.core.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetOrder {
    Mono<Order> getOrder(String orderId);
    Flux<Order> getOrders();
}

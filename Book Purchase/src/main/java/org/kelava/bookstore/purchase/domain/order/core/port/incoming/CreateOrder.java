package org.kelava.bookstore.purchase.domain.order.core.port.incoming;

import org.kelava.bookstore.purchase.domain.order.core.Order;
import org.kelava.bookstore.purchase.domain.order.core.command.CreateOrderCommand;
import reactor.core.publisher.Mono;

public interface CreateOrder {
    Mono<Order> handle(CreateOrderCommand command);
}

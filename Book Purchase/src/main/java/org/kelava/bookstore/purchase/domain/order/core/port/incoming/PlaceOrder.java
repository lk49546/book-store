package org.kelava.bookstore.purchase.domain.order.core.port.incoming;

import org.kelava.bookstore.purchase.domain.order.core.Order;
import org.kelava.bookstore.purchase.domain.order.core.command.PlaceOrderCommand;
import reactor.core.publisher.Mono;

public interface PlaceOrder {
    Mono<Order> handle(PlaceOrderCommand command);
}

package org.kelava.bookstore.purchase.domain.order.core.port.incoming;

import reactor.core.publisher.Mono;
import org.kelava.bookstore.purchase.domain.order.core.Order;
import org.kelava.bookstore.purchase.domain.order.core.command.AddItemToOrderCommand;

public interface AddItemToOrder {
    Mono<Order> handle(AddItemToOrderCommand commad);
}

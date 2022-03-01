package org.kelava.bookstore.purchase.domain.order.infrastructure.mongo;

import lombok.RequiredArgsConstructor;
import org.kelava.bookstore.purchase.domain.order.core.port.outgoing.OrderDatabase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.purchase.domain.order.core.Order;

@RequiredArgsConstructor
public class OrderDatabaseAdapter implements OrderDatabase {

    private final OrderRepository orderRepository;

    @Override
    public Mono<Order> save(final Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public Mono<Order> findById(final String id) {
        return this.orderRepository.findById(id);
    }

    @Override
    public Flux<Order> findAll() {
        return this.orderRepository.findAll();
    }
}

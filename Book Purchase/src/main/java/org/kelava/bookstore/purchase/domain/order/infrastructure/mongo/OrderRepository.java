package org.kelava.bookstore.purchase.domain.order.infrastructure.mongo;

import org.kelava.bookstore.purchase.domain.order.core.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
}

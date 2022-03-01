package org.kelava.bookstore.purchase.infrastructure;

import lombok.RequiredArgsConstructor;
import org.kelava.bookstore.purchase.domain.order.core.*;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.AddItemToOrder;
import org.kelava.bookstore.purchase.domain.order.core.port.outgoing.OrderDatabase;
import org.kelava.bookstore.purchase.domain.order.infrastructure.mongo.OrderDatabaseAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.kelava.bookstore.purchase.domain.order.core.mapper.BookServiceMapper;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.CreateOrder;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.GetOrder;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.PlaceOrder;
import org.kelava.bookstore.purchase.domain.order.core.port.outgoing.GetInventory;
import org.kelava.bookstore.purchase.domain.order.core.port.outgoing.GetLoyaltyPoints;
import org.kelava.bookstore.purchase.domain.order.core.port.outgoing.OrderEventPublisher;
import org.kelava.bookstore.purchase.domain.order.infrastructure.mongo.OrderRepository;

@Configuration
@RequiredArgsConstructor
public class OrderDomainConfiguration {

    private final GetLoyaltyPoints getLoyaltyPoints;
    private final GetInventory getInventory;

    @Bean
    public DiscountProcessor discountProcessor() {
        return new MultiDiscountProcessor(
                new LoyaltyDiscountProcessor(this.getLoyaltyPoints, 10),
                new BookTypeDiscountProcessor(),
                new QuantityDiscountProcessor());
    }

    @Bean
    public OrderDatabase orderDatabaseAdapter(final OrderRepository orderRepository) {
        return new OrderDatabaseAdapter(orderRepository);
    }

    @Bean
    public BookServiceMapper bookServiceAdapter() {
        return new BookServiceMapper();
    }

    @Bean
    @Qualifier("CreateOrder")
    public CreateOrder createOrder(final OrderDatabase orderDatabase,
                                   final OrderEventPublisher orderEventPublisher,
                                   final DiscountProcessor discountProcessor,
                                   final BookServiceMapper bookServiceAdapter) {
        return new OrderFacade(orderDatabase, orderEventPublisher, this.getInventory, discountProcessor, bookServiceAdapter);
    }

    @Bean
    @Qualifier("GetOrder")
    public GetOrder getOrder(final OrderDatabase orderDatabase,
                             final OrderEventPublisher orderEventPublisher,
                             final DiscountProcessor discountProcessor,
                             final BookServiceMapper bookServiceAdapter) {
        return new OrderFacade(orderDatabase, orderEventPublisher, this.getInventory, discountProcessor, bookServiceAdapter);
    }

    @Bean
    @Qualifier("AddItemToOrder")
    public AddItemToOrder addItemToOrder(final OrderDatabase orderDatabase,
                                         final OrderEventPublisher orderEventPublisher,
                                         final DiscountProcessor discountProcessor,
                                         final BookServiceMapper bookServiceAdapter) {
        return new OrderFacade(orderDatabase, orderEventPublisher, this.getInventory, discountProcessor, bookServiceAdapter);
    }

    @Bean
    @Qualifier("PlaceOrder")
    public PlaceOrder placeOrder(final OrderDatabase orderDatabase,
                                 final OrderEventPublisher orderEventPublisher,
                                 final DiscountProcessor discountProcessor,
                                 final BookServiceMapper bookServiceAdapter) {
        return new OrderFacade(orderDatabase, orderEventPublisher, this.getInventory, discountProcessor, bookServiceAdapter);
    }

}

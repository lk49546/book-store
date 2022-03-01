package org.kelava.bookstore.purchase.domain.order.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kelava.bookstore.purchase.domain.order.core.command.AddItemToOrderCommand;
import org.kelava.bookstore.purchase.domain.order.core.command.CreateOrderCommand;
import org.kelava.bookstore.purchase.domain.order.core.command.PlaceOrderCommand;
import org.kelava.bookstore.purchase.domain.order.core.mapper.BookServiceMapper;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.AddItemToOrder;
import org.kelava.bookstore.purchase.domain.order.core.port.outgoing.OrderDatabase;
import org.kelava.bookstore.purchase.domain.order.core.port.outgoing.OrderEventPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.purchase.domain.order.core.error.OutOfStockException;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.CreateOrder;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.GetOrder;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.PlaceOrder;
import org.kelava.bookstore.purchase.domain.order.core.port.outgoing.GetInventory;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class OrderFacade implements PlaceOrder, AddItemToOrder, CreateOrder, GetOrder {

    private final OrderDatabase orderDatabase;

    private final OrderEventPublisher orderEventPublisher;

    private final GetInventory getInventory;
    private final DiscountProcessor discountProcessor;

    private final BookServiceMapper bookServiceAdapter;

    @Override
    public Mono<Order> handle(@Valid final PlaceOrderCommand command) {
        //in real-world scenario we would check if this order belongs to the customer to prevent fraudulent behaviour
        log.info("Placing order with command: {}", command);
        final Mono<Order> order = this.orderDatabase.findById(command.orderId())
                                                    .switchIfEmpty(Mono.error(new IllegalArgumentException("Order does not exist")));

        return order.map(o -> {
            //TODO: find a way to deal with duplicates
//            o.getOrderItems().stream().map(i -> this.getInventory.findByBookId(i.getBook().getBookId()).filter(res -> res.quantity() < i.getQuantity()))
            final ShippingDetails.ShippingDetailsBuilder shippingDetailsBuilder = ShippingDetails.builder();

            o.setShippingDetails(fillShippingDetails(command, shippingDetailsBuilder));

            o.setOrderStatus(OrderStatus.FINISHED);

            return o;
        }).flatMap(this.orderDatabase::save).doOnSuccess(o -> {
            final Map<String, Integer> bookStockUpdate = o.getOrderItems()
                                                          .stream()
                                                          .collect(Collectors.toMap(i -> i.getBook()
                                                                                          .getBookId(), OrderItem::getQuantity));

            this.orderEventPublisher.emitNotification(o.getId(), o.getCustomer().getCustomerId(), bookStockUpdate);
        });
    }

    private static ShippingDetails fillShippingDetails(final PlaceOrderCommand command,
                                                       final ShippingDetails.ShippingDetailsBuilder shippingDetailsBuilder) {
        return shippingDetailsBuilder.address(command.address())
                                     .country(command.country())
                                     .county(command.county())
                                     .postalCode(command.postalCode()).build();
    }

    @Override
    public Mono<Order> handle(@Valid final CreateOrderCommand command) {

        final Order order = new Order();
        order.setOrderStatus(OrderStatus.CREATED);
        order.setTotalQuantity(0);
        //emulating customer, should be pulled from external source (separate microservice)
        order.setCustomer(new Customer(command.customerId(), command.name()));
        return this.orderDatabase.save(order)
                                 .doOnNext(o -> log.info("Creating order with id={}", o.getId()));
    }

    @Override
    public Mono<Order> handle(@Valid final AddItemToOrderCommand command) {
        final Mono<Order> order = this.orderDatabase.findById(command.orderId())
                                                    .switchIfEmpty(Mono.error(new IllegalArgumentException("Order does not exist")))
                                                    .handle((o, sink) -> {
                                                        if (o.getOrderStatus() == OrderStatus.CREATED) {
                                                            sink.next(o);
                                                        } else {
                                                            sink.error(new IllegalStateException("Order must not be already placed"));
                                                        }
                                                    });

        //check if book is on stock and available
        final Mono<Book> book = this.getInventory.findByBookId(command.bookId())
                                                 .switchIfEmpty(Mono.error(new IllegalArgumentException("Book does not exist")))
                                                 .filter(bookResponse -> bookResponse.stock() >= command.quantity())
                                                 .switchIfEmpty(Mono.error(new OutOfStockException()))
                                                 .map(bookResponse -> this.bookServiceAdapter.toBook(bookResponse));

        final Mono<Order> combinedOrder = order.zipWith(book, (o, b) -> {
            o.getOrderItems().stream().filter(i -> i.getBook().getBookId().equals(b.getBookId()))
             .findFirst()
             .ifPresentOrElse(i -> i.incrementQuantity(command.quantity()), () -> {
                 final OrderItem orderItem = OrderItem.builder().book(b)
                                                      .quantity(command.quantity())
                                                      .costPerUnit(b.getBasePrice())
                                                      .build();

                 o.addItemToOrder(orderItem);
                 o.incrementQuantity(orderItem.getQuantity());
             });
            return o;
        });

        return this.discountProcessor.process(combinedOrder.map(Order::cleanDiscount))
                                     .map(Order::calculateTotal)
                                     .flatMap(this.orderDatabase::save)
                                     .doOnSuccess(o -> {
                                         final Map<String, Object> payloadMap = new HashMap<>();

                                         final Map<String, Integer> stockUpdateMap = new HashMap<>();
                                         payloadMap.put("customerId", o.getCustomer().getCustomerId());

                                         o.getOrderItems()
                                          .forEach(i -> stockUpdateMap.put(i.getBook().getBookId(), i.getQuantity()));
                                         payloadMap.put("stockUpdate", stockUpdateMap);
                                     });
    }


    @Override
    public Mono<Order> getOrder(final String orderId) {
        return this.orderDatabase.findById(orderId);
    }

    @Override
    public Flux<Order> getOrders() {
        return this.orderDatabase.findAll();
    }
}

package org.kelava.bookstore.purchase.domain.order.application.handler;

import lombok.RequiredArgsConstructor;
import org.kelava.bookstore.purchase.domain.order.application.dto.AddItemToOrderRequest;
import org.kelava.bookstore.purchase.domain.order.application.dto.PlaceOrderRequest;
import org.kelava.bookstore.purchase.domain.order.application.validator.BasicValidator;
import org.kelava.bookstore.purchase.domain.order.core.Order;
import org.kelava.bookstore.purchase.domain.order.core.command.AddItemToOrderCommand;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.AddItemToOrder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.purchase.domain.order.application.mapper.OrderApplicationMapper;
import org.kelava.bookstore.purchase.domain.order.application.dto.CreateOrderRequest;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.CreateOrder;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.GetOrder;
import org.kelava.bookstore.purchase.domain.order.core.port.incoming.PlaceOrder;

import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class OrderHttpHandler {

    private final CreateOrder createOrder;
    private final PlaceOrder placeOrder;
    private final AddItemToOrder addItemToOrder;
    private final GetOrder getOrder;

    private final OrderApplicationMapper orderApplicationMapper;
    private final BasicValidator basicValidator;

    public Mono<ServerResponse> createOrder(final ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateOrderRequest.class)
                            .map(this.basicValidator::validate)
                            .map(this.orderApplicationMapper::toCreateOrderCommand)
                            .flatMap(this.createOrder::handle)
                            .flatMap(order -> created(URI.create("/api/v1/orders/" + order.getId())).build());
    }

    public Mono<ServerResponse> addItemToOrder(final ServerRequest serverRequest) {
        final String id = serverRequest.pathVariable("id");

        return serverRequest.bodyToMono(AddItemToOrderRequest.class)
                            .map(this.basicValidator::validate)
                            .map(req -> new AddItemToOrderCommand(id, req.bookId(), req.quantity()))
                            .flatMap(this.addItemToOrder::handle)
                            .flatMap(order -> ok().build());
    }

    public Mono<ServerResponse> placeOrder(final ServerRequest serverRequest) {
        final String id = serverRequest.pathVariable("id");

        return serverRequest.bodyToMono(PlaceOrderRequest.class)
                            .map(this.basicValidator::validate)
                            .map(i -> this.orderApplicationMapper.toPlaceOrderCommand(i, id))
                            .flatMap(this.placeOrder::handle)
                            .flatMap(order -> ok().build());
    }

    public Mono<ServerResponse> all(ServerRequest serverRequest) {
        return ok().body(this.getOrder.getOrders(), Order.class);
    }

    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        final String id = serverRequest.pathVariable("id");
        return ok().body(this.getOrder.getOrder(id), Order.class);
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return null;
    }
}

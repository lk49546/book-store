package org.kelava.bookstore.purchase.domain.order.application.mapper;

import org.kelava.bookstore.purchase.domain.order.application.dto.PlaceOrderRequest;
import org.kelava.bookstore.purchase.domain.order.core.command.CreateOrderCommand;
import org.kelava.bookstore.purchase.domain.order.core.command.PlaceOrderCommand;
import org.springframework.stereotype.Component;
import org.kelava.bookstore.purchase.domain.order.application.dto.CreateOrderRequest;

@Component
public class OrderApplicationMapper {

    public CreateOrderCommand toCreateOrderCommand(final CreateOrderRequest createOrderRequest) {
        return new CreateOrderCommand(createOrderRequest.customerId(), createOrderRequest.customerName());
    }

    public PlaceOrderCommand toPlaceOrderCommand(final PlaceOrderRequest placeOrderRequest, final String id) {
        return new PlaceOrderCommand(id, placeOrderRequest.address(),
                                     placeOrderRequest.country(), placeOrderRequest.county(),
                                     placeOrderRequest.postalCode());
    }
}

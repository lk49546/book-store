package org.kelava.bookstore.purchase.domain.order.core.command;

import javax.validation.constraints.NotBlank;

public record PlaceOrderCommand(@NotBlank String orderId, @NotBlank String address, @NotBlank String country,
                                @NotBlank String county, @NotBlank String postalCode) {}

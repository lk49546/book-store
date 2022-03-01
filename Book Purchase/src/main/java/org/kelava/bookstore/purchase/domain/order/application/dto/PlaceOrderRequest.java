package org.kelava.bookstore.purchase.domain.order.application.dto;

import javax.validation.constraints.NotBlank;

public record PlaceOrderRequest(@NotBlank String address, @NotBlank String country,
                                @NotBlank String county, @NotBlank String postalCode) {
}

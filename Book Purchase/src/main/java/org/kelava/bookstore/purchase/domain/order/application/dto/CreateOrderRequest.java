package org.kelava.bookstore.purchase.domain.order.application.dto;

import javax.validation.constraints.NotBlank;

public record CreateOrderRequest(@NotBlank String customerId, String customerName) {
}

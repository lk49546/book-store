package org.kelava.bookstore.purchase.domain.order.core.command;

import javax.validation.constraints.NotBlank;

public record CreateOrderCommand(@NotBlank String customerId, @NotBlank String name) {}

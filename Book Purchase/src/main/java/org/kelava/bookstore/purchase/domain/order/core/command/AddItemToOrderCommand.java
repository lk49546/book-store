package org.kelava.bookstore.purchase.domain.order.core.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record AddItemToOrderCommand(@NotBlank String orderId, @NotBlank String bookId, @NotNull Integer quantity) {}

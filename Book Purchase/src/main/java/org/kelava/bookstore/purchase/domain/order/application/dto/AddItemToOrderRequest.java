package org.kelava.bookstore.purchase.domain.order.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record AddItemToOrderRequest(@NotBlank String bookId, @NotNull Integer quantity) {
}

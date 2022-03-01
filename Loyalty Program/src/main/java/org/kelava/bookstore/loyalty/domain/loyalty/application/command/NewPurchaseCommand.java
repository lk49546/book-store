package org.kelava.bookstore.loyalty.domain.loyalty.application.command;

import javax.validation.constraints.NotBlank;

public record NewPurchaseCommand(@NotBlank String customerId) {
}

package org.kelava.bookstore.loyalty.domain.loyalty.core.repository.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record SaveLoyaltyCommand(@NotBlank String customerId, @NotNull Integer count) {
}

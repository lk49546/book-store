package org.kelava.bookstore.loyalty.domain.loyalty.core.repository.command;

import javax.validation.constraints.NotBlank;

public record IncrementCountCommand(@NotBlank String customerId) {
}

package org.kelava.bookstore.purchase.domain.order.core.dto;

import java.math.BigDecimal;

public record BookResponse(String id, String name, String isbn, String language, String author, String publisher,
                           Integer publishingYear, BigDecimal basePrice, Integer stock, String bookType) {}

package org.kelava.bookstore.inventory.domain.book.application.dto;

import java.math.BigDecimal;

public record BookResponse(String id, String name, String bookType, String isbn, String language, String author, String publisher,
                           Integer publishingYear, BigDecimal basePrice, Integer stock) {}

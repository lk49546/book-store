package org.kelava.bookstore.purchase.domain.order.core;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Value
public class Book {

    private String bookId;
    private String bookType;
    private String name;
    private BigDecimal basePrice;
}

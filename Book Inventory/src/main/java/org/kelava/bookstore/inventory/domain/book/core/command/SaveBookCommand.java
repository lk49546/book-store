package org.kelava.bookstore.inventory.domain.book.core.command;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class SaveBookCommand {
    private String name;
    private String isbn;
    private String language;
    private String bookType;
    //in real-world scenario, this should be separate aggregate
    private String author;
    //same thing
    private String publisher;
    private Integer publishingYear;
    private BigDecimal basePrice;
    private Integer stock;
}

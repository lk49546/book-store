package org.kelava.bookstore.inventory.domain.book.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateNewBookRequest {
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

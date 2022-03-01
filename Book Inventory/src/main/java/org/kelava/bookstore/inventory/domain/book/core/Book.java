package org.kelava.bookstore.inventory.domain.book.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Book {
    private String id;
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
    private Instant createdAt;
    private Instant updatedAt;
}

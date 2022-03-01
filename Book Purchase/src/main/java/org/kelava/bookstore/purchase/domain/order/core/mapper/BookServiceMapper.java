package org.kelava.bookstore.purchase.domain.order.core.mapper;

import org.kelava.bookstore.purchase.domain.order.core.Book;
import org.kelava.bookstore.purchase.domain.order.core.dto.BookResponse;

public class BookServiceMapper {

    public Book toBook(final BookResponse bookResponse) {
        final Book book = Book.builder().bookId(bookResponse.id())
                              .name(bookResponse.name())
                              .basePrice(bookResponse.basePrice())
                              .bookType(bookResponse.bookType())
                              .build();
        return book;
    }
}

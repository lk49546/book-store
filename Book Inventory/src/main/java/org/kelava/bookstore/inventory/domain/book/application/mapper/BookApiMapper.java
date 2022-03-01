package org.kelava.bookstore.inventory.domain.book.application.mapper;

import org.kelava.bookstore.inventory.domain.book.application.dto.BookResponse;
import org.kelava.bookstore.inventory.domain.book.application.dto.CreateNewBookRequest;
import org.kelava.bookstore.inventory.domain.book.core.command.SaveBookCommand;
import org.kelava.bookstore.inventory.domain.book.core.Book;

public class BookApiMapper {

    public BookResponse toBookResponse(final Book book) {
        return new BookResponse(book.getId(), book.getName(), book.getBookType(), book.getIsbn(), book.getLanguage(), book.getAuthor(), book.getPublisher(), book.getPublishingYear(), book.getBasePrice(), book.getStock());
    }

    public SaveBookCommand toSaveBookCommand(final CreateNewBookRequest createNewBookRequest) {
        return SaveBookCommand.builder()
                              .bookType(createNewBookRequest.getBookType())
                              .author(createNewBookRequest.getAuthor())
                              .basePrice(createNewBookRequest.getBasePrice())
                              .isbn(createNewBookRequest.getIsbn())
                              .language(createNewBookRequest.getLanguage())
                              .publisher(createNewBookRequest.getPublisher())
                              .publishingYear(createNewBookRequest.getPublishingYear())
                              .name(createNewBookRequest.getName())
                              .stock(createNewBookRequest.getStock())
                              .build();
    }
}

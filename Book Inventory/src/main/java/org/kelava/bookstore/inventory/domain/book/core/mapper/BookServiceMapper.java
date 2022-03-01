package org.kelava.bookstore.inventory.domain.book.core.mapper;

import org.kelava.bookstore.inventory.domain.book.core.command.SaveBookCommand;
import org.kelava.bookstore.inventory.domain.book.core.Book;

public class BookServiceMapper {

    public Book toBook(final SaveBookCommand command) {
        final Book book = new Book();
        book.setBookType(command.getBookType());
        book.setAuthor(command.getAuthor());
        book.setBasePrice(command.getBasePrice());
        book.setIsbn(command.getIsbn());
        book.setLanguage(command.getLanguage());
        book.setPublisher(command.getPublisher());
        book.setPublishingYear(command.getPublishingYear());
        book.setName(command.getName());
        book.setStock(command.getStock());
        return book;
    }
}

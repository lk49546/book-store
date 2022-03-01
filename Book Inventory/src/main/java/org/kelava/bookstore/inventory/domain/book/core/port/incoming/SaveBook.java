package org.kelava.bookstore.inventory.domain.book.core.port.incoming;

import org.kelava.bookstore.inventory.domain.book.core.command.SaveBookCommand;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.inventory.domain.book.core.Book;

public interface SaveBook {

    Mono<Book> save(SaveBookCommand command);
}

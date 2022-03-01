package org.kelava.bookstore.inventory.domain.book.core.port.outgoing;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.inventory.domain.book.core.Book;

public interface BookDatabaseAdapter {

    Mono<Book> findById(String id);
    Mono<Book> findByIsbn(String isbn);
    Flux<Book> findAll();
    Mono<Book> save(Book book);

    Mono<Book> decrementStock(String id, Integer quantity);
}

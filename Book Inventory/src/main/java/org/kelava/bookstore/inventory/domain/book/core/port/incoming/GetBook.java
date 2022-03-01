package org.kelava.bookstore.inventory.domain.book.core.port.incoming;

import org.kelava.bookstore.inventory.domain.book.core.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetBook {

    Flux<Book> findAll();

    Mono<Book> findByIsbn(String isbn);

    Mono<Book> findById(String id);

}

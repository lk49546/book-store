package org.kelava.bookstore.inventory.domain.book.infrastructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.inventory.domain.book.core.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Mono<Book> findByIsbn(String isbn);
}

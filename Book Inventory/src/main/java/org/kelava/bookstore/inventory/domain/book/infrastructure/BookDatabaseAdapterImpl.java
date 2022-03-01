package org.kelava.bookstore.inventory.domain.book.infrastructure;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.inventory.domain.book.core.Book;
import org.kelava.bookstore.inventory.domain.book.core.port.outgoing.BookDatabaseAdapter;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@RequiredArgsConstructor
public class BookDatabaseAdapterImpl implements BookDatabaseAdapter {

    private final BookRepository bookRepository;
    private final ReactiveMongoOperations mongoOperations;

    @Override
    public Mono<Book> findById(final String id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Mono<Book> findByIsbn(final String isbn) {
        return this.bookRepository.findByIsbn(isbn);
    }

    @Override
    public Flux<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Mono<Book> save(final Book book) {
        return this.bookRepository.save(book);
    }

    @Override
    public Mono<Book> decrementStock(final String id, final Integer quantity) {
        return this.mongoOperations.update(Book.class)
                                   .matching(query(where("_id").is(new ObjectId(id)).and("stock").gte(quantity)))
                                   .apply(new Update().inc("stock", -quantity))
                                   .findAndModify();

    }
}

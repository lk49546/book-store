package org.kelava.bookstore.inventory.domain.book.core;

import lombok.RequiredArgsConstructor;
import org.kelava.bookstore.inventory.domain.book.core.command.DecrementStockCommand;
import org.kelava.bookstore.inventory.domain.book.core.command.SaveBookCommand;
import org.kelava.bookstore.inventory.domain.book.core.mapper.BookServiceMapper;
import org.kelava.bookstore.inventory.domain.book.core.port.incoming.DecrementStock;
import org.kelava.bookstore.inventory.domain.book.core.port.incoming.SaveBook;
import org.kelava.bookstore.inventory.domain.book.core.port.outgoing.BookDatabaseAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.inventory.domain.book.core.port.incoming.GetBook;

import java.util.Map;

@RequiredArgsConstructor
public class BookFacade implements GetBook, SaveBook, DecrementStock {

    private final BookDatabaseAdapter bookDatabaseAdapter;
    private final BookServiceMapper bookServiceMapper;

    @Override
    public Flux<Book> findAll() {
        return this.bookDatabaseAdapter.findAll();
    }

    @Override
    public Mono<Book> findById(final String id) {
        return this.bookDatabaseAdapter.findById(id)
                                       .switchIfEmpty(Mono.error(new IllegalArgumentException("Book with id=" + id + " not found")));
    }

    @Override
    public Mono<Book> findByIsbn(final String isbn) {
        return this.bookDatabaseAdapter.findByIsbn(isbn).switchIfEmpty(Mono.error(new IllegalArgumentException("Book with isbn=" + isbn + " not found")));
    }

    @Override
    public Mono<Book> save(final SaveBookCommand command) {
        return this.bookDatabaseAdapter.save(this.bookServiceMapper.toBook(command));
    }

    @Override
    public Flux<Book> decrementStock(final DecrementStockCommand command) {
        Flux<Book> result = Flux.empty();
        for (Map.Entry<String, Integer> entry : command.getStockUpdateMap().entrySet()) {
            result = result.concatWith(this.bookDatabaseAdapter.decrementStock(entry.getKey(), entry.getValue()));
        }
        return result;
    }
}

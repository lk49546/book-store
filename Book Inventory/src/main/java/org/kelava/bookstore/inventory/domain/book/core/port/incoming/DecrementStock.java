package org.kelava.bookstore.inventory.domain.book.core.port.incoming;

import reactor.core.publisher.Flux;
import org.kelava.bookstore.inventory.domain.book.core.Book;
import org.kelava.bookstore.inventory.domain.book.core.command.DecrementStockCommand;

public interface DecrementStock {

    Flux<Book> decrementStock(DecrementStockCommand command);
}

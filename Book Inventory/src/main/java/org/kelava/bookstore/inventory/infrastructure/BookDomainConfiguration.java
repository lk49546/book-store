package org.kelava.bookstore.inventory.infrastructure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.kelava.bookstore.inventory.domain.book.application.mapper.BookApiMapper;
import org.kelava.bookstore.inventory.domain.book.core.BookFacade;
import org.kelava.bookstore.inventory.domain.book.core.mapper.BookServiceMapper;
import org.kelava.bookstore.inventory.domain.book.core.port.incoming.DecrementStock;
import org.kelava.bookstore.inventory.domain.book.core.port.incoming.GetBook;
import org.kelava.bookstore.inventory.domain.book.core.port.incoming.SaveBook;
import org.kelava.bookstore.inventory.domain.book.core.port.outgoing.BookDatabaseAdapter;
import org.kelava.bookstore.inventory.domain.book.infrastructure.BookDatabaseAdapterImpl;
import org.kelava.bookstore.inventory.domain.book.infrastructure.BookRepository;

@Configuration
public class BookDomainConfiguration {

    @Bean
    public BookApiMapper bookApiMapper() {
        return new BookApiMapper();
    }

    @Bean
    public BookDatabaseAdapter bookDatabaseAdapter(final BookRepository bookRepository, final ReactiveMongoOperations mongoOperations) {
        return new BookDatabaseAdapterImpl(bookRepository, mongoOperations);
    }

    @Bean
    public BookServiceMapper bookServiceMapper() {
        return new BookServiceMapper();
    }

    @Bean
    @Qualifier("GetBook")
    public GetBook getBook(final BookDatabaseAdapter bookDatabaseAdapter, final BookServiceMapper bookServiceMapper) {
        return new BookFacade(bookDatabaseAdapter, bookServiceMapper);
    }

    @Bean
    @Qualifier("SaveBook")
    public SaveBook saveBook(final BookDatabaseAdapter bookDatabaseAdapter, final BookServiceMapper bookServiceMapper) {
        return new BookFacade(bookDatabaseAdapter, bookServiceMapper);
    }

    @Bean
    @Qualifier("DecrementStock")
    public DecrementStock decrementStock(final BookDatabaseAdapter bookDatabaseAdapter, final BookServiceMapper bookServiceMapper) {
        return new BookFacade(bookDatabaseAdapter, bookServiceMapper);
    }
}

package org.kelava.bookstore.inventory.domain.book.application;

import lombok.RequiredArgsConstructor;
import org.kelava.bookstore.inventory.domain.book.application.dto.BookResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.inventory.domain.book.application.dto.CreateNewBookRequest;
import org.kelava.bookstore.inventory.domain.book.application.mapper.BookApiMapper;
import org.kelava.bookstore.inventory.domain.book.core.port.incoming.GetBook;
import org.kelava.bookstore.inventory.domain.book.core.port.incoming.SaveBook;

import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
@Component
public class BookHttpHandler {

    private final BookApiMapper bookApiMapper;
    @Qualifier("GetBook")
    private final GetBook getBook;
    @Qualifier("SaveBook")
    private final SaveBook saveBook;

    public Mono<ServerResponse> all(final ServerRequest serverRequest) {
        return serverRequest.queryParam("isbn")
                            .map(isbn -> ok().body(getBook.findByIsbn(isbn)
                                                          .map(this.bookApiMapper::toBookResponse), BookResponse.class))
                            .orElse(ok().body(getBook.findAll()
                                                     .map(this.bookApiMapper::toBookResponse), BookResponse.class));
    }

    public Mono<ServerResponse> get(final ServerRequest serverRequest) {
        final String id = serverRequest.pathVariable("id");
        return ok().body(getBook.findById(id).map(this.bookApiMapper::toBookResponse), BookResponse.class);
    }

    public Mono<ServerResponse> save(final ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateNewBookRequest.class)
                            .flatMap(i -> this.saveBook.save(this.bookApiMapper.toSaveBookCommand(i)))
                            .map(this.bookApiMapper::toBookResponse)
                            .flatMap(r -> created(URI.create("/api/v1/books/" + r.id())).build());

    }
}

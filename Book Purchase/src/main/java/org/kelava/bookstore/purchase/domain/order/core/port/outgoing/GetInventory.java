package org.kelava.bookstore.purchase.domain.order.core.port.outgoing;

import org.kelava.bookstore.purchase.domain.order.core.dto.BookResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "inventory", url = "http://inventory:8000")
public interface GetInventory {

    @GetMapping("/api/v1/books/{bookId}")
    Mono<BookResponse> findByBookId(@PathVariable("bookId") String bookId);
}
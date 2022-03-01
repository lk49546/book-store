package org.kelava.bookstore.inventory.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.kelava.bookstore.inventory.domain.book.application.BookHttpHandler;
import org.kelava.bookstore.inventory.domain.book.application.dto.CreateNewBookRequest;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class WebConfiguration implements WebFluxConfigurer {

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/v1/books", beanClass = BookHttpHandler.class, beanMethod = "all", method = RequestMethod.GET, operation = @Operation(operationId = "all", summary = "Fetch all books")),
            @RouterOperation(path = "/api/v1/books", beanClass = BookHttpHandler.class, beanMethod = "save", method = RequestMethod.POST, operation = @Operation(operationId = "save", summary = "Save book", requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = CreateNewBookRequest.class))))),
            @RouterOperation(path = "/api/v1/books/{id}", beanClass = BookHttpHandler.class, beanMethod = "get", method = RequestMethod.GET, operation = @Operation(operationId = "get", summary = "Get book by id"))
    })
    public RouterFunction<ServerResponse> routes(final BookHttpHandler bookHttpHandler) {

        //quick and dirty api versioning, probably could be done in a better way
        return route().GET("/api/v1/books/{id}", bookHttpHandler::get)
                      .GET("/api/v1/books", bookHttpHandler::all)
                      .POST("/api/v1/books", bookHttpHandler::save)
                      .build();
    }
}
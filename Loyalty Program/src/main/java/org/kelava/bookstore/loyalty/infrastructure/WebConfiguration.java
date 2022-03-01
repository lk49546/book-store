package org.kelava.bookstore.loyalty.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import org.kelava.bookstore.loyalty.domain.loyalty.application.LoyaltyHttpHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class WebConfiguration implements WebFluxConfigurer {

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/v1/loyalty/{id}", beanClass = LoyaltyHttpHandler.class, beanMethod = "get", method = RequestMethod.GET, operation = @Operation(operationId = "get", description = "Get loyalty points for customer"))
    })
    public RouterFunction<ServerResponse> routes(final LoyaltyHttpHandler loyaltyHttpHandler) {

        final RouterFunction<ServerResponse> routerFunction = route().GET("/api/v1/loyalty/{id}", loyaltyHttpHandler::get)
                                                                      .build();
        return routerFunction;
    }
}
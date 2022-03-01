package org.kelava.bookstore.purchase.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.kelava.bookstore.purchase.domain.order.application.dto.AddItemToOrderRequest;
import org.kelava.bookstore.purchase.domain.order.application.dto.CreateOrderRequest;
import org.kelava.bookstore.purchase.domain.order.application.dto.PlaceOrderRequest;
import org.kelava.bookstore.purchase.domain.order.application.handler.OrderHttpHandler;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebConfiguration {

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/v1/orders", beanClass = OrderHttpHandler.class, beanMethod = "all", method = RequestMethod.GET, operation = @Operation(operationId = "all")),
            @RouterOperation(path = "/api/v1/orders", beanClass = OrderHttpHandler.class, beanMethod = "createOrder", method = RequestMethod.POST, operation = @Operation(operationId = "createOrder", requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = CreateOrderRequest.class))))),
            @RouterOperation(path = "/api/v1/orders/{id}", beanClass = OrderHttpHandler.class, beanMethod = "get", method = RequestMethod.GET, operation = @Operation(operationId = "get", parameters = @Parameter(name = "id", in = ParameterIn.PATH, required = true))),
            @RouterOperation(path = "/api/v1/orders/{id}/items", method = RequestMethod.POST, beanClass = OrderHttpHandler.class, beanMethod = "addItemToOrder",operation = @Operation(operationId = "addItemToOrder", requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = AddItemToOrderRequest.class))))),
            @RouterOperation(path = "/api/v1/orders/{id}/placement", beanClass = OrderHttpHandler.class, beanMethod = "placeOrder", method = RequestMethod.POST, operation = @Operation(operationId = "placeOrder", requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = PlaceOrderRequest.class))))),
    })
    public RouterFunction<ServerResponse> routes(final OrderHttpHandler orderHttpHandler) {

        return route().GET("/api/v1/orders", orderHttpHandler::all)
                      .POST("/api/v1/orders", orderHttpHandler::createOrder)
                      .GET("/api/v1/orders/{id}", orderHttpHandler::get)
                      .POST("/api/v1/orders/{id}/items", orderHttpHandler::addItemToOrder)
                      .DELETE("/api/v1/orders/{id}", orderHttpHandler::delete)
                      .POST("/api/v1/orders/{id}/placement", orderHttpHandler::placeOrder)
                      .build();
    }

    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
}
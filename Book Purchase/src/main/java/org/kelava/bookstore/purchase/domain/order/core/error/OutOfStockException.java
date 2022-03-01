package org.kelava.bookstore.purchase.domain.order.core.error;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
        super(message);
    }
}

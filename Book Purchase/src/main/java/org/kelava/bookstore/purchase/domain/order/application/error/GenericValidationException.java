package org.kelava.bookstore.purchase.domain.order.application.error;

public class GenericValidationException extends RuntimeException {

    public GenericValidationException() {
    }

    public GenericValidationException(final String message) {
        super(message);
    }

    public GenericValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}

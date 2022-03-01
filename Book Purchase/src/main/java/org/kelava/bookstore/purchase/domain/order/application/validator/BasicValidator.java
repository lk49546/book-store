package org.kelava.bookstore.purchase.domain.order.application.validator;

import lombok.RequiredArgsConstructor;
import org.kelava.bookstore.purchase.domain.order.application.error.GenericValidationException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class BasicValidator {

    private final Validator beanValidator;

    private <T> T validateWithBeanValidator(final T object) {
        final Set<ConstraintViolation<T>> violations = beanValidator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return object;
    }

    public <T> T validate(final T t) throws GenericValidationException,
            ConstraintViolationException {
        Objects.requireNonNull(t);
        return validateWithBeanValidator(t);
    }
}

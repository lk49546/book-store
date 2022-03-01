package org.kelava.bookstore.purchase.domain.order.core;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {

    CREATED(0), CANCELLED(1), FINISHED(2);

    private final Integer value;

    public Integer getValue() {
        return this.value;
    }
}

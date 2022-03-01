package org.kelava.bookstore.purchase.domain.order.core;

import lombok.*;

@Value
@Builder
public class Customer {

    private String customerId;
    private String name;
}

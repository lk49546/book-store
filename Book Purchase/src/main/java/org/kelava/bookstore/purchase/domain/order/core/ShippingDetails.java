package org.kelava.bookstore.purchase.domain.order.core;

import lombok.*;

@Builder
@Value
public class ShippingDetails {

    private String address;
    private String postalCode;
    private String country;
    private String county;
}

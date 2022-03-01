package org.kelava.bookstore.purchase.domain.order.core;

import lombok.*;

import java.math.BigDecimal;

@Value
@Builder
public class Discount {

    private DiscountType type;
    private BigDecimal discount;
    private BigDecimal discountAmount;

    enum DiscountType {
        QUANTITY, TYPE, LOYALTY, PROMOTION;
    }
}

package org.kelava.bookstore.purchase.domain.order.core;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class QuantityDiscountProcessor implements DiscountProcessor {

    protected static Discount.DiscountType type = Discount.DiscountType.QUANTITY;

    @Override
    public Mono<Order> process(final Mono<Order> order) {

        return order.map(o -> {
            if (o.getTotalQuantity() >= 3) {
                for (OrderItem orderItem : o.getOrderItems()) {
                    final Book book = orderItem.getBook();
                    switch (book.getBookType()) {
                        case "Regular" -> {
                            final BigDecimal discount = BigDecimal.valueOf(0.10).setScale(2, RoundingMode.HALF_UP);
                            Order.applyDiscount(type, discount, orderItem);
                        }
                        case "Old edition" -> {
                            final BigDecimal discount = BigDecimal.valueOf(0.05).setScale(2, RoundingMode.HALF_UP);
                            Order.applyDiscount(type, discount, orderItem);
                        }
                    }
                }
            }
            return o;
        });
    }
}

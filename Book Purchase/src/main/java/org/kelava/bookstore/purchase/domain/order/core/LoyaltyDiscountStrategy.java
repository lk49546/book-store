package org.kelava.bookstore.purchase.domain.order.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public interface LoyaltyDiscountStrategy {

    static LoyaltyDiscountStrategy discountLeastExpensiveStrategy() {
        return order -> {
            final Predicate<OrderItem> discountableEditions = b -> b.getBook()
                                                                    .getBookType()
                                                                    .equalsIgnoreCase("Regular") || b.getBook().getBookType().equalsIgnoreCase("Old edition");

            final Optional<OrderItem> leastExpensiveBook = order.getOrderItems()
                                                                .stream()
                                                                .filter(discountableEditions)
                                                                .min(Comparator.comparing(OrderItem::getCostPerUnit));

            leastExpensiveBook.ifPresent(item -> {
                Order.applyDiscount(LoyaltyDiscountProcessor.type, BigDecimal.valueOf(1.00).setScale(2, RoundingMode.HALF_UP), item);
            });
            return order;
        };
    }

    Order discount(Order order);
}

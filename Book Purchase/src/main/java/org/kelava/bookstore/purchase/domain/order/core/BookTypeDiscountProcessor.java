package org.kelava.bookstore.purchase.domain.order.core;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BookTypeDiscountProcessor implements DiscountProcessor {

    protected static Discount.DiscountType type = Discount.DiscountType.TYPE;
    private static final BigDecimal OLD_RELEASE_DISCOUNT = BigDecimal.valueOf(0.20).setScale(2, RoundingMode.HALF_UP);

    @Override
    public Mono<Order> process(final Mono<Order> order) {

        return order.map(o -> {
            for (OrderItem orderItem : o.getOrderItems()) {
                final Book book = orderItem.getBook();
                final String bookType = book.getBookType();
                if ("Old edition".equalsIgnoreCase(bookType)) {
                    Order.applyDiscount(type, OLD_RELEASE_DISCOUNT, orderItem);
                }
            }
            return o;
        });
    }
}

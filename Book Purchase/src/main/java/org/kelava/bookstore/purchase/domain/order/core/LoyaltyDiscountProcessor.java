package org.kelava.bookstore.purchase.domain.order.core;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import org.kelava.bookstore.purchase.domain.order.core.port.outgoing.GetLoyaltyPoints;

@RequiredArgsConstructor
public class LoyaltyDiscountProcessor implements DiscountProcessor {

    protected static final Discount.DiscountType type = Discount.DiscountType.LOYALTY;

    private final GetLoyaltyPoints getLoyaltyPoints;
    private final Integer loyaltyPointsAward;

    private final LoyaltyDiscountStrategy discountStrategy = LoyaltyDiscountStrategy.discountLeastExpensiveStrategy();

    @Override
    public Mono<Order> process(final Mono<Order> order) {
        return order.flatMap(o -> getLoyaltyPoints.findLoyaltyByCustomerId(o.getCustomer().getCustomerId())
                               .filter(loyaltyResponse -> loyaltyResponse.count().equals(loyaltyPointsAward))
                               .map(loyalty -> discount(o))
                               .defaultIfEmpty(o));

    }

    private Order discount(final Order order) {
        return discountStrategy.discount(order);
    }
}

package org.kelava.bookstore.purchase.domain.order.core;

import reactor.core.publisher.Mono;

public class MultiDiscountProcessor implements DiscountProcessor {

    private final DiscountProcessor[] discountProcessors;

    public MultiDiscountProcessor(final DiscountProcessor... discountProcessors) {
        this.discountProcessors = discountProcessors;
    }

    @Override
    public Mono<Order> process(final Mono<Order> order) {
        Mono<Order> changeableOrder = order;
        for (DiscountProcessor discountProcessor : this.discountProcessors) {
            changeableOrder = discountProcessor.process(changeableOrder);
        }
        return changeableOrder;
    }
}

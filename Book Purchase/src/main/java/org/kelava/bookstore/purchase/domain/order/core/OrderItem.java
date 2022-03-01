package org.kelava.bookstore.purchase.domain.order.core;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

@Builder
@Getter
public class OrderItem {

    @NotNull
    private final Book book;
    @NotNull
    private Integer quantity;
    @NotNull
    private final BigDecimal costPerUnit;
    private final List<Discount> discounts;

    public OrderItem(Book book, Integer quantity, BigDecimal costPerUnit,
                     List<Discount> discounts) {
        this.book = book;
        this.quantity = quantity;
        this.costPerUnit = costPerUnit;
        this.discounts = discounts == null ? new ArrayList<>() : discounts;
    }

    public List<Discount> getDiscounts() {
        return Collections.unmodifiableList(this.discounts);
    }

    public void addToDiscounts(final Discount discount) {
        this.discounts.add(discount);
    }


    public void clearDiscounts() {
        this.discounts.clear();
    }

    public void incrementQuantity(final Integer quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("added quantity must be greater than 0");
        }
        this.quantity += quantity;
    }

    public void decrementQuantity(final Integer quantity) {
        if (quantity > this.quantity) {
                throw new IllegalArgumentException("subtracted quantity must be less or equal to existing quantity");
        }
        this.quantity -= quantity;
    }
}

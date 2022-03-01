package org.kelava.bookstore.purchase.domain.order.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("Order")
public class Order {
    private String id;
    private Customer customer;
    private Instant orderDate;
    private Integer totalQuantity = 0;
    private BigDecimal total = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP);
    private ShippingDetails shippingDetails;
    //in real-world scenario this should be further examined and expanded
    private OrderStatus orderStatus;
    private Set<OrderItem> orderItems = new HashSet<>();
    private Instant createdAt;
    private Instant updatedAt;

    public void addItemToOrder(final OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public void removeItemFromOrder(final OrderItem orderItem) {
        this.orderItems.remove(orderItem);
    }

    public Set<OrderItem> getOrderItems() {
        return Collections.unmodifiableSet(this.orderItems);
    }

    public static void applyDiscount(final Discount.DiscountType type, final BigDecimal discountPercentage, final OrderItem orderItem) {
        final BigDecimal discountAmount = orderItem.getCostPerUnit()
                                                   .multiply(discountPercentage)
                                                   .setScale(2, RoundingMode.HALF_UP);
        final Discount.DiscountBuilder builder = Discount.builder().type(type).discount(discountPercentage).discountAmount(discountAmount);
        orderItem.addToDiscounts(builder.build());

    }

    public void incrementQuantity(final Integer quantity) {
        this.totalQuantity += quantity;
    }

    public Order calculateTotal() {
        for (OrderItem item : this.getOrderItems()) {
            BigDecimal discountedCostPerUnit = item.getCostPerUnit();
            for (Discount discount : item.getDiscounts()) {
                discountedCostPerUnit = discountedCostPerUnit.subtract(discount.getDiscountAmount());
            }
            if (BigDecimal.ZERO.compareTo(discountedCostPerUnit) == 1) {
                discountedCostPerUnit = BigDecimal.ZERO;
            }
            this.total = this.total.add(discountedCostPerUnit.multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return this;
    }

    public Order cleanDiscount() {
        for (OrderItem orderItem : this.orderItems) {
            orderItem.clearDiscounts();
        }
        return this;
    }
}

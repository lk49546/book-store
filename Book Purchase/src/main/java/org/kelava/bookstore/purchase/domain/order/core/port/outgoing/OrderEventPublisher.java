package org.kelava.bookstore.purchase.domain.order.core.port.outgoing;

import java.util.Map;

public interface OrderEventPublisher {

    void emitNotification(String eventId, String customerId, Map<String, Integer> payload);
}

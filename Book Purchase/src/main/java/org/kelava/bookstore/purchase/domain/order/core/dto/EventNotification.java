package org.kelava.bookstore.purchase.domain.order.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Map;

@Builder
@RequiredArgsConstructor
@Getter
public class EventNotification {

    private final String eventId;
    private final String customerId;
    private final Map<String, Integer> payload;

}

package org.kelava.bookstore.loyalty.domain.loyalty.application.dto;

import lombok.Data;

import java.util.Map;

@Data
public class EventNotification {

    private String eventId;
    private String customerId;
    private Map<String, Integer> payload;
}

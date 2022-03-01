package org.kelava.bookstore.purchase.domain.order.infrastructure.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kelava.bookstore.purchase.domain.order.core.dto.EventNotification;
import org.kelava.bookstore.purchase.domain.order.core.port.outgoing.OrderEventPublisher;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublisherAdapter implements OrderEventPublisher {

    private final ReactiveKafkaProducerTemplate<String, EventNotification> reactiveKafkaProducerTemplate;

    @org.springframework.beans.factory.annotation.Value(value = "purchase")
    private String purchaseTopic;


    public void emitNotification(String eventId, String customerId, Map<String, Integer> payload) {
        final EventNotification eventNotification = new EventNotification(eventId, customerId, payload);
        this.reactiveKafkaProducerTemplate.send(purchaseTopic, eventNotification)
                                          .doOnSuccess(senderResult -> log.info("sent {} offset : {}", eventNotification, senderResult.recordMetadata().offset()))
                                          .subscribe();
    }

}

package org.kelava.bookstore.loyalty.domain.loyalty.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.kelava.bookstore.loyalty.domain.loyalty.application.command.NewPurchaseCommand;
import org.kelava.bookstore.loyalty.domain.loyalty.application.dto.EventNotification;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import org.kelava.bookstore.loyalty.domain.loyalty.core.LoyaltyUseCase;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoyaltyConsumerHandler {

    private final ReactiveKafkaConsumerTemplate<String, EventNotification> reactiveKafkaConsumerTemplate;
    private final LoyaltyUseCase loyaltyUseCase;

    @PostConstruct
    private void consumeNewPurchaseEvents() {
        reactiveKafkaConsumerTemplate.receiveAutoAck()
                                     // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                                     .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}", consumerRecord.key(), consumerRecord.value(), consumerRecord.topic(), consumerRecord.offset()))
                                     .map(ConsumerRecord::value)
                                     .doOnNext(command -> log.info("successfully consumed {}={}", EventNotification.class.getSimpleName(), command))
                                     .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()))
                                     //TODO: needs to be in transaction, otherwise fail and repeat event emission
                                     .concatMap(e -> this.loyaltyUseCase.incrementLoyaltyPoints(new NewPurchaseCommand(e.getCustomerId())))
                                     .subscribe();
    }

}

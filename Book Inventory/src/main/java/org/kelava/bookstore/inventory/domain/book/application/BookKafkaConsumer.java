package org.kelava.bookstore.inventory.domain.book.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import org.kelava.bookstore.inventory.domain.book.application.dto.EventNotification;
import org.kelava.bookstore.inventory.domain.book.core.command.DecrementStockCommand;
import org.kelava.bookstore.inventory.domain.book.core.port.incoming.DecrementStock;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Slf4j
@Component
public class BookKafkaConsumer {

    private final ReactiveKafkaConsumerTemplate<String, EventNotification> reactiveKafkaConsumerTemplate;
    private final DecrementStock decrementStock;

    @PostConstruct
    private void consumeDecrementStockEvents() {
        reactiveKafkaConsumerTemplate
                .receiveAutoAck()
                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                                                     consumerRecord.key(),
                                                     consumerRecord.value(),
                                                     consumerRecord.topic(),
                                                     consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(command -> log.info("successfully consumed {}={}", EventNotification.class.getSimpleName(), command))
                .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()))
                //TODO: needs to be in transaction, otherwise fail and repeat event emission
                .concatMap(e -> this.decrementStock.decrementStock(new DecrementStockCommand(e.getPayload())))
                .subscribe();
    }
}
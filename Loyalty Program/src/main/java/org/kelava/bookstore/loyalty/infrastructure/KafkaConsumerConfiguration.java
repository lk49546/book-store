package org.kelava.bookstore.loyalty.infrastructure;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.kelava.bookstore.loyalty.domain.loyalty.application.dto.EventNotification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {
    @Bean
    public ReceiverOptions<String, EventNotification> kafkaReceiverOptions(
            @Value(value = "purchase") final String topic, final KafkaProperties kafkaProperties) {

        final Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getConsumer().getBootstrapServers());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaProperties.getConsumer().getClientId());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomEventNotificationDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

        final ReceiverOptions<String, EventNotification> basicReceiverOptions = ReceiverOptions.create(props);
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    public static class CustomEventNotificationDeserializer implements Deserializer<EventNotification> {

        @Override
        public EventNotification deserialize(String topic, byte[] data) {
            final JsonDeserializer<EventNotification> deserializer = new JsonDeserializer<>(EventNotification.class);
            deserializer.setRemoveTypeHeaders(false);
            deserializer.addTrustedPackages("*");
            deserializer.setUseTypeMapperForKey(true);
            return deserializer.deserialize(topic, data);
        }
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, EventNotification> reactiveKafkaConsumerTemplate(
            final ReceiverOptions<String, EventNotification> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }
}
package org.universe.opensky.notification.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class MultiTopicConsumer {

    @KafkaListener(topics = {"product-order-topic1", "product-order-topic2"}, groupId = "order-notification-group")
    public void consume(ConsumerRecord<String, String> record,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        
        System.out.printf("Received message: %s from Topic: %s%n", record.value(), topic);
        
        // Route logic based on topic
        if ("product-order-topic1".equals(topic)) {
            // handle order
        } else if ("product-order-topic2".equals(topic)) {
            // handle payment
        }
    }
}
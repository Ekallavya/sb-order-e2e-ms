package org.universe.opensky.notification.util;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ManualOffsetConsumer {
    public static void main(String[] args) {
        // 1. Configure the consumer properties
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "order-notification-group");
        
        // Disable automatic offset commits
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        
        // Define fallback strategy if no previous committed offset exists
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // 2. Initialize the consumer and subscribe to a topic
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList("product-order-topic1"));
            System.out.println("Consumer initialized. Listening for messages...");

            // 3. Execution poll loop
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                
                for (ConsumerRecord<String, String> record : records) {
                    try {
                        // Simulate critical business logic processing (e.g., Database write)
                        processBusinessLogic(record.key(), record.value());
                    } catch (Exception e) {
                        System.err.println("Failed to process record. Skipping commit for this batch: " + e.getMessage());
                        // Handle failure (retry, send to DLQ, or break the loop to retry the batch)
                        continue; 
                    }
                }

                // 4. Safely commit offsets synchronously after processing the entire batch
                if (!records.isEmpty()) {
                    try {
                        consumer.commitSync();
                        System.out.println("Batch offsets successfully committed to __consumer_offsets.");
                    } catch (Exception e) {
                        System.err.println("Commit failed due to consumer group rebalance: " + e.getMessage());
                    }
                }
            }
        }
    }

    private static void processBusinessLogic(String key, String value) throws InterruptedException {
        System.out.printf("Processing Transaction -> Key: %s, Value: %s%n", key, value);
        Thread.sleep(50); // Simulate network/DB latency
    }
}
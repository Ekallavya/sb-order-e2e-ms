package org.universe.opensky.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "product-order-topic" ,groupId = "order-notification-group")
    public void listen(String orderPlacedEvent){
        log.info("Got Message from order-placed topic {}", orderPlacedEvent);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("madhureddie@gmail.com");
            messageHelper.setTo("madhureddie@gmail.com");
            messageHelper.setSubject("Your Order with OrderNumber is placed successfully");

            messageHelper.setText(String.format("""
                            Dear Customer

                            Your order with order number is now placed successfully
                            please find your order details %s.

                            Best Regards
                            Spring Shop
                            """,
                    orderPlacedEvent));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Order Notification email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RuntimeException("Exception occurred when sending mail to springshop@email.com", e);
        }
    }

    @KafkaListener(
            id = "multi-topic-partition-listener1",
            groupId = "order-notification-group",
            topicPartitions = {
                    @TopicPartition(topic = "product-order-topic1", partitions = { "0", "2" }),
                    @TopicPartition(topic = "product-order-topic2", partitions = { "0","1" })
            }
    )
    public void poll(ConsumerRecord<String, String> record) {
        System.out.printf("Topic: %s | Partition: %d | Offset: %d | Key: %s | Value: %s%n",
                record.topic(),
                record.partition(),
                record.offset(),
                record.key(),
                record.value());
    }

    @KafkaListener(
            id = "multi-topic-partition-listener2",
            groupId = "order-notification-group",
            topicPartitions = {
                    @TopicPartition(topic = "product-order-topic1", partitions = { "1", "2" }),
                    @TopicPartition(topic = "product-order-topic2", partitions = { "0","2" })
            }
    )
    public void consume(ConsumerRecord<String, String> record) {
        System.out.printf("Topic: %s | Partition: %d | Offset: %d | Key: %s | Value: %s%n",
                record.topic(),
                record.partition(),
                record.offset(),
                record.key(),
                record.value());
    }


}
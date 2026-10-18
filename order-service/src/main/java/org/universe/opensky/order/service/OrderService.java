package org.universe.opensky.order.service;

import jakarta.websocket.SendResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.universe.opensky.order.config.InventoryClient;
import org.universe.opensky.order.dto.OrderRequest;
import org.universe.opensky.order.event.OrderPlacedEvent;
import org.universe.opensky.order.model.Order;
import org.universe.opensky.order.repository.OrderRepository;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        boolean inStock = inventoryClient.isInStock(orderRequest.skucode(), orderRequest.quantity());
        if (inStock) {
            var order = mapToOrder(orderRequest);
            orderRepository.save(order);
            var orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.user()
                    .email(),orderRequest.user().fName(),
                    orderRequest.user().lName());
            log.info("Start- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
            String orderDetails =orderPlacedEvent.getOrderNumber()+"-"+orderPlacedEvent.getFName()+"-"+orderPlacedEvent.getLName()+"-"+orderPlacedEvent.getEmail();
            //CompletableFuture<SendResult> compFuture =
                    kafkaTemplate.send("product-order-topic", orderDetails).thenAcceptAsync(sendResult ->{System.out.println("OrderPlacedEvent sent to Kafka Topic :"+sendResult);});

                kafkaTemplate.send("product-order-topic1", 0,"Krish1" ,"From Arjun-0 :"+orderDetails).thenAcceptAsync(sendResult ->{System.out.println("OrderPlacedEvent sent to Kafka Order Topic1-Partition-0 :"+sendResult);});
                kafkaTemplate.send("product-order-topic1", 1,"Krish1" ,"From Arjun-1 :"+orderDetails).thenAcceptAsync(sendResult ->{System.out.println("OrderPlacedEvent sent to Kafka Order Topic1-Partition-1 :"+sendResult);});
                kafkaTemplate.send("product-order-topic1", 2,"Krish1" ,"From Arjun-2 :"+orderDetails).thenAcceptAsync(sendResult ->{System.out.println("OrderPlacedEvent sent to Kafka Order Topic1-Partition-2 :"+sendResult);});

                kafkaTemplate.send("product-order-topic2", 0,"Krish2" ,"From Hanuman-1 :"+orderDetails).thenAcceptAsync(sendResult ->{System.out.println("OrderPlacedEvent sent to Kafka Order Topic2-Partition-0 :"+sendResult);});
                kafkaTemplate.send("product-order-topic2", 1,"Krish2" ,"From Hanuman-1 :"+orderDetails).thenAcceptAsync(sendResult ->{System.out.println("OrderPlacedEvent sent to Kafka Order Topic2-Partition-1 :"+sendResult);});
                kafkaTemplate.send("product-order-topic2", 2,"Krish2" ,"From Hanuman-2 :"+orderDetails).thenAcceptAsync(sendResult ->{System.out.println("OrderPlacedEvent sent to Kafka Order Topic2-Partition-2 :"+sendResult);});

            log.info("End- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);

        } else {
            throw new RuntimeException("Product with skucode " + orderRequest.skucode() + "is not in stock");
        }
    }


    private static Order mapToOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkucode(orderRequest.skucode());
        return order;
    }
}
// Example of sending message to Kafka and handling the result asynchronously using CompletableFuture
//kafkaTemplate.send("topicName", msg)
//    .completable()
//    .whenComplete((result, e) -> {
//        if (e != null) {
//        System.out.println("failed to send: " + e.getMessage());
//        } else {
//        System.out.println("sent to partition: " + result.getRecordMetadata().partition());
//        }
//        });
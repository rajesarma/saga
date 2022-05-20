package com.sarma.saga.orderservice.publisher;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.sarma.saga.lib.entity.Order;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderPublisher {

    @Value("${queue.payment}")
    private String queue;

    private QueueMessagingTemplate queueMessagingTemplate;

    public OrderPublisher(AmazonSQSAsync amazonSQSAsync) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    }

    public void publish(Order order) {
        log.info("Publishing order Id :: {}", order.getOrderId());
        queueMessagingTemplate.convertAndSend(queue, order);
    }
}

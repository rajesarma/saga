package com.sarma.saga.paymentservice.publisher;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.sarma.saga.lib.entity.Order;
import com.sarma.saga.lib.entity.Payment;
import com.sarma.saga.lib.model.OrderStatus;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentPublisher {

    @Value("${queue.order}")
    private String orderQueue;

    @Value("${queue.restaurant}")
    private String restaurantQueue;

    private QueueMessagingTemplate queueMessagingTemplate;

    public PaymentPublisher(AmazonSQSAsync amazonSQSAsync) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    }

    public void publish(Payment payment) {

        // TODO Handle logic if payment failed, then we need not to publish to restaurant service

        log.info("Publishing payment for order Id :: {}", payment.getOrderId());
        queueMessagingTemplate.convertAndSend(restaurantQueue, payment);
    }

    public void publish(OrderStatus orderStatus) {
        log.info("Publishing order status Id :: {}", orderStatus.getOrderId());
        queueMessagingTemplate.convertAndSend(orderQueue, orderStatus);
    }
}

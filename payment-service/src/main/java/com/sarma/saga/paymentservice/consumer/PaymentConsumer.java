package com.sarma.saga.paymentservice.consumer;

import com.sarma.saga.lib.entity.Order;
import com.sarma.saga.paymentservice.service.PaymentService;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentConsumer {

    private PaymentService paymentService;

    public PaymentConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @SqsListener(value = "${queue.payment}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
//    @SqsListener("https://sqs.ap-south-1.amazonaws.com/984920617125/payment-queue")
    public void consume(Order order) {
        log.info("Received Order details :: {}", order.getOrderId());
        paymentService.send(order);
    }
}

package com.sarma.saga.orderservice.consumer;

import com.sarma.saga.lib.entity.Order;
import com.sarma.saga.lib.model.OrderStatus;
import com.sarma.saga.orderservice.repository.OrderRepository;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Slf4j
@Service
public class OrderConsumer {

    private final OrderRepository orderRepository;

    public OrderConsumer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @SqsListener("order-queue")
    public void consume(OrderStatus orderStatus) {
        log.info("Received Order status :: {}", orderStatus);
        Order order = orderRepository.getOrderById(orderStatus.getOrderId());
        order.setOrderStatus(orderStatus.getOrderState().name());
        order.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
        orderRepository.save(order);
    }
}

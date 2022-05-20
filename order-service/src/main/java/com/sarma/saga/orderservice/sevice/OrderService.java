package com.sarma.saga.orderservice.sevice;

import com.sarma.saga.lib.model.OrderRequest;
import com.sarma.saga.lib.model.OrderResponse;
import com.sarma.saga.lib.entity.Order;
import com.sarma.saga.orderservice.publisher.OrderPublisher;
import com.sarma.saga.orderservice.repository.OrderRepository;
import com.sarma.saga.lib.util.ConverterUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements com.sarma.saga.lib.service.OrderService {

    private final OrderRepository orderRepository;
    private final OrderPublisher orderPublisher;

    public OrderService(OrderRepository orderRepository,
                        OrderPublisher orderPublisher) {
        this.orderRepository = orderRepository;
        this.orderPublisher = orderPublisher;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = ConverterUtils.convert(orderRequest);
        orderRepository.save(order);
        orderPublisher.publish(order);
        return ConverterUtils.convert(order);
    }

    @Override
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepository.getOrderById(orderId);
        return ConverterUtils.convert(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.getOrders().stream().map(ConverterUtils::convert).collect(Collectors.toList());
    }

    /*@Override
    public OrderResponse getOrder(OrderRequest orderRequest) {
        Order order = orderRepository.getOrderById(orderRequest.getOrderId());
        return ConverterUtils.convert(order);
    }*/
}

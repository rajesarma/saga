package com.sarma.saga.orderservice.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.sarma.saga.lib.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class OrderRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public OrderRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Order save(Order order) {
        dynamoDBMapper.save(order);
        return order;
    }

    public List<Order> getOrders() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return dynamoDBMapper.scan(Order.class, scanExpression);
    }

    public Order getOrderById(String orderId) {
        Order order;
        try {
            order = dynamoDBMapper.load(Order.class, orderId);
        } catch (Exception e) {
            log.error(e.getMessage());
            order = new Order();
        }
        return order;
    }
}

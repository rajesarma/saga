package com.sarma.saga.paymentservice.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.sarma.saga.lib.entity.Order;
import com.sarma.saga.lib.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class PaymentRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public PaymentRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Payment save(Payment payment) {
        dynamoDBMapper.save(payment);
        return payment;
    }
}

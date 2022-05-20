package com.sarma.saga.paymentservice.service;

import com.sarma.saga.lib.entity.Order;
import com.sarma.saga.lib.entity.Payment;
import com.sarma.saga.lib.model.OrderState;
import com.sarma.saga.lib.model.OrderStatus;
import com.sarma.saga.lib.model.PaymentState;
import com.sarma.saga.lib.util.ConverterUtils;
import com.sarma.saga.paymentservice.publisher.PaymentPublisher;
import com.sarma.saga.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private PaymentPublisher paymentPublisher;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentPublisher paymentPublisher) {
        this.paymentRepository = paymentRepository;
        this.paymentPublisher = paymentPublisher;
    }

    public void send (Order order) {
        Payment payment = ConverterUtils.convertOrderToPayment(order);

        paymentRepository.save(payment); // Persist in Payment Table
        payment.setPaymentStatus(PaymentState.PAYMENT_SUCCESS.name());

        paymentPublisher.publish(payment);// Publishing to Restaurant queue

        OrderStatus orderStatus = OrderStatus.builder()
                .orderId(order.getOrderId())
                .orderState(OrderState.ORDER_PAID)
                .message("Order Paid through " + payment.getPaymentMethod())
                .build();
        paymentPublisher.publish(orderStatus);// Publishing to Order queue
    }

}

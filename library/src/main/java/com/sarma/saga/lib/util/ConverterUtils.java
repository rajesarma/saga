package com.sarma.saga.lib.util;

import com.sarma.saga.lib.entity.Payment;
import com.sarma.saga.lib.model.OrderResponse;
import com.sarma.saga.lib.constants.Constants;
import com.sarma.saga.lib.entity.Order;
import com.sarma.saga.lib.model.OrderRequest;
import com.sarma.saga.lib.model.ProductDto;

import java.sql.Timestamp;
import java.util.Objects;

public class ConverterUtils {

    public static Order convert(OrderRequest orderRequest) {
        String now = new Timestamp(System.currentTimeMillis()).toString();
        return Order.builder()
                .customerId(orderRequest.getCustomerId())
                .sellerId(orderRequest.getSellerId())
                .product(com.sarma.saga.lib.entity.Product.builder()
                        .productId(orderRequest.getProduct().getProductId())
                        .productName(orderRequest.getProduct().getProductName())
                        .build())
                .paymentMethod(orderRequest.getPaymentMethod().name()) // TODO
                .deliveryLocation(orderRequest.getDeliveryLocation())
                .orderStatus(Constants.ORDER_CREATED)
                .createdOn(now)
                .updatedOn(now)
                .build();
    }

    public static OrderResponse convert(Order order) {
        String now = new Timestamp(System.currentTimeMillis()).toString();
        OrderResponse orderResponse = new OrderResponse();
        if (Objects.nonNull(order.getOrderId())) {
            orderResponse.setOrderId(order.getOrderId());
        }
        orderResponse.setCustomerId(order.getCustomerId());
        orderResponse.setSellerId(order.getSellerId());
        orderResponse.setProduct(ProductDto.builder()
                        .productId(order.getProduct().getProductId())
                        .productName(order.getProduct().getProductName())
                        .build());
        orderResponse.setPaymentMethod(order.getPaymentMethod()); // TODO
        orderResponse.setDeliveryLocation(order.getDeliveryLocation());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setCreatedOn(now);
        orderResponse.setUpdatedOn(now);
        return orderResponse;
    }

    public static Payment convertOrderToPayment(Order order) {
        String now = new Timestamp(System.currentTimeMillis()).toString();
        return Payment.builder()
                .orderId(order.getOrderId())
                .customerId(order.getCustomerId())
                .sellerId(order.getSellerId())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatusDescription("Paid through " + order.getPaymentMethod())
                .createdOn(now)
                .updatedOn(now)
                .build();
    }
}

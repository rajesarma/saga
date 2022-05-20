package com.sarma.saga.lib.service;

import com.sarma.saga.lib.model.OrderRequest;
import com.sarma.saga.lib.model.OrderResponse;

import java.util.List;

public interface OrderService {

     OrderResponse createOrder(OrderRequest orderRequest);
//     OrderResponse getOrder(OrderRequest orderRequest);
     OrderResponse getOrderById(String orderId);
     List<OrderResponse> getAllOrders();
}

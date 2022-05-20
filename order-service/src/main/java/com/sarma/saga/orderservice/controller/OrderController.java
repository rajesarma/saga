package com.sarma.saga.orderservice.controller;

import com.sarma.saga.lib.model.OrderRequest;
import com.sarma.saga.lib.model.OrderResponse;
import com.sarma.saga.orderservice.sevice.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("")
    public ResponseEntity<List<OrderResponse>> getOrderById(
    ) {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable String orderId
    ) {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<OrderResponse> getOrders(
            @RequestBody OrderRequest orderRequest
            ) {
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }
}

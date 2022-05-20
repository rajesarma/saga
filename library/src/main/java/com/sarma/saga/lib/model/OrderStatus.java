package com.sarma.saga.lib.model;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderStatus {
    private String orderId;
    private OrderState orderState;
    private String message;
}

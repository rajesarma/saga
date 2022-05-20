package com.sarma.saga.lib.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String orderId;
    private Integer customerId;
    private Integer sellerId;
    private ProductDto product;
    private String paymentMethod;
    private String deliveryLocation;
    private String orderStatus;
    private String createdOn;
    private String updatedOn;
}

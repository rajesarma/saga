package com.sarma.saga.lib.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@DynamoDBDocument
public class Product {

    @DynamoDBAttribute
    private Integer productId;

    @DynamoDBAttribute
    private String productName;
}

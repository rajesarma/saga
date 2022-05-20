package com.sarma.saga.paymentservice.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.config.SimpleMessageListenerContainerFactory;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.QueueMessageHandler;
import io.awspring.cloud.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    @Profile("dev")
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildDb());
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQSAsync());
    }

    @Bean
    // AmazonSQSAsync is an interface for accessing the SQS asynchronously.
    // Each asynchronous method will return a Java Future object representing the asynchronous operation.
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withRegion(region)
                .withClientConfiguration(new ClientConfiguration().withConnectionTimeout(5000))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    private AmazonDynamoDB buildDb() {
        String serviceEndpoint = String.format("dynamodb.%s.amazonaws.com", region);
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                serviceEndpoint, region);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(accessKey, secretKey)
        );
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider)
                .build();
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(
            AmazonSQSAsync amazonSQSAsync,
            QueueMessageHandler queueMessageHandler
    ) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setAmazonSqs(amazonSQSAsync);
        simpleMessageListenerContainer.setMessageHandler(queueMessageHandler);
        simpleMessageListenerContainer.setMaxNumberOfMessages(10);
//        simpleMessageListenerContainer.setAutoStartup(true);
        return simpleMessageListenerContainer;
    }

    @Bean
    public QueueMessageHandler queueMessageHandler(AmazonSQSAsync amazonSQSAsync) {
        QueueMessageHandlerFactory queueMessageHandlerFactory = new QueueMessageHandlerFactory();
        queueMessageHandlerFactory.setAmazonSqs(amazonSQSAsync);
        return queueMessageHandlerFactory.createQueueMessageHandler();
    }

    /*@Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSqs) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSqs);
        factory.setMaxNumberOfMessages(10);
        factory.setWaitTimeOut(2);
        return factory;
    }*/

}

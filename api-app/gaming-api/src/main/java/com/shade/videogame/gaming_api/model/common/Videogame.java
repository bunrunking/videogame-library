package com.shade.videogame.gaming_api.model.common;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Videogame {

    private String id;
    private String name;
    private String platform;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    // standard setters and getters
}
package com.shade.videogame.gaming_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shade.videogame.gaming_api.model.common.Videogame;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Service
public class LibraryService {
    @Autowired
    private DynamoDbTemplate dynamoDbTemplate;

    @Autowired
    private DynamoDbClient dynamoDBClient;

    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    public Videogame get(String id){
        Key.Builder keyBuilder = Key.builder().partitionValue(id);
        Key key = keyBuilder.build();

        return dynamoDbTemplate.load(key, Videogame.class);
    }

    public List<Videogame> getAll() {
        return dynamoDbTemplate.scanAll(Videogame.class)
            .items()
            .stream()
            .collect(Collectors.toList());

        /*
        return dynamoDbEnhancedClient
            .table("Videogame", TableSchema.fromBean(Videogame.class))
            .scan()
            .items()
            .stream()
            .collect(Collectors.toList());
        */
    }

    public List<String> listAllTables() {
        return dynamoDBClient.listTables().tableNames();
    }
}

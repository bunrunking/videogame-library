package com.shade.videogame.gaming_api.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    private final Logger log = LoggerFactory.getLogger(RestClientConfig.class);
    
    @Value("${rawg.api.url}")
    private String rawgApiUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(rawgApiUrl)
                .build();
    }
}
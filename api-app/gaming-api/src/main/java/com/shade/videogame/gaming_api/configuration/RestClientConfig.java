package com.shade.videogame.gaming_api.configuration;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    private final Logger log = LoggerFactory.getLogger(RestClientConfig.class);
    
    @Value("${rawg.api.url}")
    private String rawgApiUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .requestFactory(httpRequestFactory())
                .baseUrl(rawgApiUrl)
                .requestInterceptor(loggingInterceptor())
                .build();
    }

    private ClientHttpRequestInterceptor loggingInterceptor() {
        return (request, body, execution) -> {
            // Log the complete URI
            log.info("Outgoing request to: {} {}", request.getMethod(), request.getURI());
            
            // Proceed with the request
            return execution.execute(request, body);
        };
    }

    private CloseableHttpClient httpClient5() {
        // Configure connection manager for pooling
        var connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnTotal(20) // Max total connections
                .setMaxConnPerRoute(20) // Max connections per route
                .build();

        // Configure request defaults (e.g., timeouts)
        var requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(5)) // Connection timeout
                .setResponseTimeout(Timeout.ofSeconds(30)) // Response timeout
                .build();

        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    private ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient5());
    }
}
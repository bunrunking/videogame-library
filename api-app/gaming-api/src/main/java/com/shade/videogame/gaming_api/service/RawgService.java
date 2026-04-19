package com.shade.videogame.gaming_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openapitools.model.GamesList200Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.shade.videogame.gaming_api.model.rawg.GameListResponse;

@Service
public class RawgService {
    Logger logger = LoggerFactory.getLogger(RawgService.class);

    @Value("${rawg.api.key}")
    private String apiKey;

    @Autowired
    private RestClient restClient;

    public GameListResponse search(String keyword) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", apiKey);
        params.add("search", keyword);
        params.add("search_precise", "true");

        GameListResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                .path("/games")
                .queryParams(params)
                .build())
                .retrieve()
                .body(GameListResponse.class);
        logger.info("Fetched games for keyword: {}, total #: {}", keyword, response.getCount());

        return response;
    }
}

package com.shade.videogame.gaming_api.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shade.videogame.gaming_api.rawg.model.GameListResponse;
import com.shade.videogame.gaming_api.rawg.service.RawgService;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    Logger logger = Logger.getLogger(SearchController.class.getName());

    @Autowired
    private RawgService rawgService;

    @GetMapping
    public GameListResponse search(@RequestParam String keyword) {
        GameListResponse response = rawgService.search(keyword);
        logger.info("Search results for '" + keyword + "': " + response);
        
        return response;
    }

}

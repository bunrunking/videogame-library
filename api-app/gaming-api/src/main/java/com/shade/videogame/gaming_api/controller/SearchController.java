package com.shade.videogame.gaming_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shade.videogame.gaming_api.service.RawgService;

@RestController
public class SearchController {
    @Autowired
    private RawgService rawgService;

    @GetMapping("/search")
    public void search(@RequestParam String keyword) {
        rawgService.search(keyword);
    }

}

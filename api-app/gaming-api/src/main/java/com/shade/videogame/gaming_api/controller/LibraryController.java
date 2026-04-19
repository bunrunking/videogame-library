package com.shade.videogame.gaming_api.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shade.videogame.gaming_api.model.rawg.GameListResponse;
import com.shade.videogame.gaming_api.model.common.Videogame;
import com.shade.videogame.gaming_api.service.LibraryService;
import com.shade.videogame.gaming_api.service.RawgService;

@RestController
@RequestMapping("/api/inventory")
public class LibraryController {
    Logger logger = Logger.getLogger(LibraryController.class.getName());

    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public List<Videogame> inventory() {
        List<Videogame> games = libraryService.getAll();
        
        return games;
    }

    @GetMapping("/tables")
    public List<String> tables() {
        return libraryService.listAllTables();
    }
    

}

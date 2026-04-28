package com.shade.videogame.gaming_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shade.videogame.gaming_api.inventory.model.Videogame;
import com.shade.videogame.gaming_api.inventory.service.VideogameService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
	private final VideogameService videogameService;
	
	public InventoryController(VideogameService videogameService) {
		this.videogameService = videogameService;
	}
    
    @GetMapping
    public Page<Videogame> retrieveGames(
        @RequestParam(required = false) String platform,
        @RequestParam(required = false) String playStatus,
        @RequestParam(required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "25") int size) {

        return videogameService.findByCriteria(platform, playStatus, title, PageRequest.of(page, size));
    }
}

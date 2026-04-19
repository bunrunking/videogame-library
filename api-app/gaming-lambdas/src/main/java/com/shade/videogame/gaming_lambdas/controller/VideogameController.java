package com.shade.videogame.gaming_lambdas.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.shade.videogame.gaming_lambdas.model.Videogame;
import com.shade.videogame.gaming_lambdas.service.VideogameService;

@EnableWebMvc
@RestController
@RequestMapping("/api/games") // Top-level path
public class VideogameController {
	private final VideogameService videogameService;
	
	public VideogameController(VideogameService videogameService) {
		this.videogameService = videogameService;
	}
    
    @GetMapping("/videogames")
    public Page<Videogame> retrieveGames(
        @RequestParam(required = false) String platform,
        @RequestParam(required = false) String playStatus,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "25") int size) {

        return videogameService.findByCriteria(platform, playStatus, PageRequest.of(page, size));
    }
}

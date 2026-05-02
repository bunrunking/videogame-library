package com.shade.videogame.gaming_api.rawg.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Game {
	private Integer id;
    private String name;

    @JsonProperty("background_image")
    private String backgroundImage;

    private List<PlatformListResponse> platforms;
}

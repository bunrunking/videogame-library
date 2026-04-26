package com.shade.videogame.gaming_api.service;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class ToolsDefinition {
	@JsonClassDescription("Gets videogames from the user's collection, wishlist, or sold list.")
	static class GetVideogameList {

	    @JsonPropertyDescription("Optional. Name of the videogame to filter by.")
	    public String title;

	    @JsonPropertyDescription("Optional. Platform of the videogame.  If not provided, will return games from all platforms.")
	    public Platform platform;

	    @JsonPropertyDescription("Which list to query.  Only use OWN if the user asks what games they have in their collection.")
	    public ListType listType;

	    public enum Platform {
	        PLAYSTATION_1, PLAYSTATION_2, PLAYSTATION_3, PLAYSTATION_4, PLAYSTATION_5,
	        XBOX, XBOX_360,
	        NINTENDO_SWITCH, NINTENDO_WII, NINTENDO_WII_U,
	        NINTENDO_GAMECUBE, NINTENDO_64,
	        SEGA_GENESIS, SEGA_SATURN, SEGA_DREAMCAST
	    }

	    public enum ListType {
	        OWN, SOLD, WISH
	    }
	}
}

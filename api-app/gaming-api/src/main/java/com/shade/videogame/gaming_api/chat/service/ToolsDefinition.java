package com.shade.videogame.gaming_api.chat.service;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.shade.videogame.gaming_api.inventory.model.ListType;
import com.shade.videogame.gaming_api.inventory.model.Platform;

public class ToolsDefinition {
	@JsonClassDescription("Gets videogames from the user's collection, wishlist, or sold list.")
	static class GetVideogameList {

	    @JsonPropertyDescription("Optional. Name of the videogame to filter by.")
	    public String title;

	    @JsonPropertyDescription("Optional. Platform of the videogame.  If not provided, will return games from all platforms.")
	    public Platform platform;

	    @JsonPropertyDescription("Which list to query.  Only use OWN if the user asks what games they have in their collection.")
	    public ListType listType;
	    
	    @JsonPropertyDescription("The pages of results to show.  Starting at 0, default is 0.  If the user asks for more results, increment this page number by 1 and call the function again with the same parameters.")
	    public String page;
	}
}

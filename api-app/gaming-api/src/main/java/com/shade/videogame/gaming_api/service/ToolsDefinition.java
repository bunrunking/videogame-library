package com.shade.videogame.gaming_api.service;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class ToolsDefinition {
    @JsonClassDescription("Gets the videogames in the user's collection or wishlist.")
    static class GetVideogameList {
        @JsonPropertyDescription("The name of the videogame.")
        public String title;

        @JsonPropertyDescription("The platform or console for the videogame.")
        public String platform;
        
        @JsonPropertyDescription("Whether this is in the user's collection or wishlist.")
        public String listType;

        public String execute() {
            return "You asked for the " + listType + " of " + title + " on " + platform + ".";
        }
    }
    
}

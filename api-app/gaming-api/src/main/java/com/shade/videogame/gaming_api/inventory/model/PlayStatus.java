package com.shade.videogame.gaming_api.inventory.model;

import java.util.HashMap;
import java.util.Map;

public enum PlayStatus {
	NOT_STARTED("Not Started"),
	IN_PROGRESS("In Progress"),
	COMPLETED("Completed"),
	ABANDONED("Abandoned");

	private String description;
	
	PlayStatus(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	private static final Map<String, PlayStatus> BY_DESCRIPTION = new HashMap<>();
	
	static {
	    for (PlayStatus playStatus : values()) {
	        BY_DESCRIPTION.put(playStatus.description.toLowerCase(), playStatus);
	    }
	}

	public static PlayStatus fromDescription(String description) {
		return BY_DESCRIPTION.get(description.toLowerCase());
	}
}

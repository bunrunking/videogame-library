package com.shade.videogame.gaming_lambdas.model;

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
}

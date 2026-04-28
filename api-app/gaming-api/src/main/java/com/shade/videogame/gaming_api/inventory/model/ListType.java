package com.shade.videogame.gaming_api.inventory.model;

public enum ListType {
	OWN("Own"),
	SOLD("Sold"),
	WISH("Wishlist");

	private String description;
	
	ListType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}

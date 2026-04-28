package com.shade.videogame.gaming_api.inventory.model;

import java.util.HashMap;
import java.util.Map;

public enum Platform {
	ATARI_2600("Atari 2600"),
	ATARI_400_800("Atari 400/800"),
	COLECOVISION("Colecovision"),
	COMMODORE_64("Commodore 64"),
	EVERCADE("Evercade"),
	MICROSOFT_XBOX("Microsoft Xbox"),
	MICROSOFT_XBOX_360("Microsoft Xbox 360"),
	NINTENDO_64("Nintendo 64"),
	NINTENDO_DS("Nintendo DS"),
	NINTENDO_GAMEBOY("Nintendo Game Boy"),
	NINTENDO_GAMEBOY_ADVANCE("Nintendo Game Boy Advance"),
	NINTENDO_GAMEBOY_COLOR("Nintendo Game Boy Color"),
	NINTENDO_GAMECUBE("Nintendo GameCube"),
	NINTENDO_SWITCH("Nintendo Switch"),
	NINTENDO_WII("Nintendo Wii"),
	NINTENDO_WII_U("Nintendo Wii U"),
	PLAYDATE("Playdate"),
	SEGA_CD("Sega CD"),
	SEGA_CD_32X("Sega CD 32X"),
	SEGA_DREAMCAST("Sega Dreamcast"),
	SEGA_GENESIS_MEGADRIVE("Sega Genesis/Mega Drive"),
	SEGA_SATURN("Sega Saturn"),
	SNES_SUPER_FAMICOM("SNES/Super Famicom"),
	SONY_PLAYSTATION("Sony PlayStation"),
	SONY_PLAYSTATION_2("Sony PlayStation 2"),
	SONY_PLAYSTATION_3("Sony PlayStation 3"),
	SONY_PLAYSTATION_4("Sony PlayStation 4"),
	SONY_PLAYSTATION_5("Sony PlayStation 5"),
	TURBOGRAFX_16("TurboGrafx 16"),
	UNKNOWN("Unknown");

	private String description;
	
	Platform(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	private static final Map<String, Platform> BY_DESCRIPTION = new HashMap<>();
	
	static {
	    for (Platform platform : values()) {
	        BY_DESCRIPTION.put(platform.description.toLowerCase(), platform);
	    }
	}

	public static Platform fromDescription(String description) {
		Platform platform = BY_DESCRIPTION.get(description.toLowerCase());
		return platform != null ? platform : UNKNOWN;
	}
}

package com.shade.videogame.gaming_api.chat.service;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ToolsRequest {
	private String functionName;
	private String callerId;
	private Map<String, String> functionArgs;	
}

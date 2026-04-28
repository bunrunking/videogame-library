package com.shade.videogame.gaming_api.chat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ChatMessage {
	private String role;
	private String content;
	@JsonProperty("tool_call_id")
	private String toolCallId;
}

package com.shade.videogame.gaming_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.conversations.Message;
import com.openai.models.responses.ResponseCreateParams;
import com.shade.videogame.gaming_api.model.common.ChatMessage;

@Service
public class ChatService {
	public void chat(List<ChatMessage> messages) {
		// Create an OpenAI client using environment variables for configuration.  This client should create parameters
		// for the chat completion request, including the model and the messages to send to the API as the user.
		/*
		OpenAIClient client = new OpenAIOkHttpClient();
		ResponseCreateParams params = ResponseCreateParams.builder()
			.model("gpt-3.5-turbo")
			.messages(messages.stream()
					.map(m -> new Message(m.getRole(), m.getContent()))
					.toList())
			.build();
		*/
	}
}

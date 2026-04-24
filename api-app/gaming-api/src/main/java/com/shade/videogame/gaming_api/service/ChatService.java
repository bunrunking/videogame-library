package com.shade.videogame.gaming_api.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputItem;
import com.shade.videogame.gaming_api.model.common.ChatMessage;

@Service
public class ChatService {
    Logger logger = Logger.getLogger(ChatService.class.getName());
    
	@Value("${openai.api.key}")
	private String openAiApiKey;
	
    @Value("classpath:prompt/system.md")
    private Resource resource;
    
	public ChatMessage chat(List<ChatMessage> messages) throws IOException {
		List<Map<String, String>> conversation = new ArrayList<Map<String, String>>();
		addConversation(conversation, "system", readPromptFile());
		addConversation(conversation, messages);
		
		// Create an OpenAI client using environment variables for configuration.  This client should create parameters
		// for the chat completion request, including the model and the messages to send to the API as the user.
		OpenAIClient client = OpenAIOkHttpClient.builder()
				.apiKey(openAiApiKey)
				.build();
		
		ObjectMapper objectMapper = new ObjectMapper();
		ResponseCreateParams params = ResponseCreateParams.builder()
		        .model("gpt-4.1-nano")
		        .addTool(ToolsDefinition.GetVideogameList.class)
		        .input(objectMapper.writeValueAsString(conversation))
		        .build();
		
		Response response = client.responses().create(params);
		logger.info("Received response from OpenAI API: " + response);
		
		ChatMessage botMessage = null;
		
		String responseText = extractText(response);
		logger.info("Parsed message text: " + responseText);
		if (!responseText.isBlank()) {
			botMessage = new ChatMessage("assistant", responseText);
		}
		
		String toolResponse = extractToolResponse(response);
		logger.info("Parsed tool response: " + toolResponse);
		if (!toolResponse.isBlank()) {
			botMessage = new ChatMessage("assistant", toolResponse);
		}
		
		return botMessage;
	}
	
	private void addConversation(List<Map<String, String>> currentConversation, List<ChatMessage> messages) {
		for (ChatMessage message : messages) {
			addConversation(currentConversation, message.getRole(), message.getContent());
		}
	}

	private List<Map<String, String>> addConversation(List<Map<String, String>> currentConversation, String persona, String message) {
		Map<String, String> exchange = Map.of("role", persona, "content", message);
		currentConversation.add(exchange);
		
		return currentConversation;
	}
	
    private String readPromptFile() throws IOException {
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
    
    private String extractText(Response response) {
        StringBuilder result = new StringBuilder();

        for (ResponseOutputItem item : response.output()) {
        	if (item.isMessage()) {
        		item.asMessage().content().forEach(content -> {
        			if (content.isOutputText()) {
						result.append(content.asOutputText().text());
					}
				});
        	}
        }

        return result.toString();
    }
    
    private String extractToolResponse(Response response) {
        StringBuilder result = new StringBuilder();

        ObjectMapper objectMapper = new ObjectMapper();
        for (ResponseOutputItem item : response.output()) {
        	if (item.isFunctionCall()) {
        		logger.info("Function call found in response: " + item);
        		
        		String argsString = item.asFunctionCall().arguments();
        		try {
					@SuppressWarnings("unchecked")
					Map<String, String> args = objectMapper.readValue(argsString, Map.class);
					
					logger.info("Parsed function call arguments: " + args);
					result.append(args);
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }

        return result.toString();
    }
}

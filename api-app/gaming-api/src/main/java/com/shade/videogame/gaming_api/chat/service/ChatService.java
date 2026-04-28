package com.shade.videogame.gaming_api.chat.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputItem;
import com.shade.videogame.gaming_api.chat.model.ChatMessage;
import com.shade.videogame.gaming_api.inventory.model.Platform;
import com.shade.videogame.gaming_api.inventory.model.PlayStatus;
import com.shade.videogame.gaming_api.inventory.model.Videogame;
import com.shade.videogame.gaming_api.inventory.service.VideogameService;

@Service
public class ChatService {
    Logger logger = Logger.getLogger(ChatService.class.getName());
    
	@Value("${openai.api.key}")
	private String openAiApiKey;
	
    @Value("classpath:prompt/system.md")
    private Resource systemResource;
    
    @Value("classpath:prompt/tools.md")
    private Resource toolsResource;
    
    @Autowired
    VideogameService videogameService;
    
	public List<ChatMessage> chat(List<ChatMessage> messages) throws IOException {
		List<ChatMessage> responseMessages = new ArrayList<ChatMessage>();
		List<Map<String, String>> conversation = new ArrayList<Map<String, String>>();
		addConversation(conversation, "system", readPromptFiles(), null);
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
		
		ChatMessage botMessage = null;
		
		String responseText = extractText(response);
		logger.info("Parsed message text: " + responseText);
		if (!responseText.isBlank()) {
			botMessage = new ChatMessage("assistant", responseText, null);
			responseMessages.add(botMessage);
		}
		
		List<ToolsRequest> toolsRequests = extractToolResponse(response);
		if (!toolsRequests.isEmpty()) {
			logger.info("Parsed tool requests: " + toolsRequests);
			
			for (ToolsRequest toolRequest : toolsRequests) {
				// Add the tool call to the conversation so that the model has the full context of the conversation and can respond appropriately after the tool is called.
				botMessage = new ChatMessage("assistant", toolRequest.getToolCall(), null);
				responseMessages.add(botMessage);
				
				String toolResult = invokeTool(toolRequest);
				
				if (toolResult != null) {
					ChatMessage toolResponseMessage = new ChatMessage("tool", toolResult, toolRequest.getCallerId());
					responseMessages.add(toolResponseMessage);
				}
			}
			
			messages.addAll(responseMessages);
			responseMessages.addAll(chat(messages));
		}
		
		return responseMessages;
	}
	
	private void addConversation(List<Map<String, String>> currentConversation, List<ChatMessage> messages) {
		for (ChatMessage message : messages) {
			addConversation(currentConversation, message.getRole(), message.getContent(), message.getToolCallId());
		}
	}

	private List<Map<String, String>> addConversation(List<Map<String, String>> currentConversation, String persona, String message, String toolCallId) {
		Map<String, String> exchange = Map.of("role", persona, "content", message);
		if (toolCallId != null) {
			exchange = Map.of("role", persona, "content", message, "tool_call_id", toolCallId);
		}
		currentConversation.add(exchange);
		
		return currentConversation;
	}
	
    private String readPromptFiles() throws IOException {
    	StringBuilder promptBuilder = new StringBuilder();
    	
    	promptBuilder.append(new String(systemResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
    	promptBuilder.append(new String(toolsResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
    	
    	return promptBuilder.toString();
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
    
    private List<ToolsRequest> extractToolResponse(Response response) throws IOException{
    	List<ToolsRequest> toolsRequest = new ArrayList<ToolsRequest>();

        ObjectMapper objectMapper = new ObjectMapper();
        for (ResponseOutputItem item : response.output()) {
        	if (item.isFunctionCall()) {
        		ToolsRequest toolRequest = new ToolsRequest();

        		toolRequest.setToolCall(objectMapper.writeValueAsString(item.asFunctionCall()));
        		toolRequest.setCallerId(item.asFunctionCall().id().get());
        		toolRequest.setFunctionName(item.asFunctionCall().name());
        		
        		String argsString = item.asFunctionCall().arguments();
        		try {
					@SuppressWarnings("unchecked")
					Map<String, String> args = objectMapper.readValue(argsString, Map.class);
	        		toolRequest.setFunctionArgs(args);
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		toolsRequest.add(toolRequest);
        	}
        }

        return toolsRequest;
    }
    
    private String invokeTool(ToolsRequest toolRequest) throws IOException {
		// Call the tool and then respond back to the user with the results of the tool call.
		if (toolRequest.getFunctionName().equals("GetVideogameList")) {
			ObjectMapper mapper = new ObjectMapper();
			
			String platform = toolRequest.getFunctionArgs().get("platform");
			String playStatus = toolRequest.getFunctionArgs().get("playStatus");
			String title = toolRequest.getFunctionArgs().get("title");
			Integer page = toolRequest.getFunctionArgs().get("page") != null ? Integer.valueOf(toolRequest.getFunctionArgs().get("page")) : 0;
			PageRequest request = PageRequest.of(page, 25);
			
			Platform platformEnum = platform != null ? Platform.valueOf(platform) : null;
			PlayStatus playStatusEnum = playStatus != null ? PlayStatus.valueOf(playStatus) : null;
			
			Page<Videogame> games = videogameService.findByCriteria(platformEnum, playStatusEnum, title, request);
			logger.info("Retrieved games from database: " + games.getContent());
			logger.info("Page info - total pages: " + games.getTotalPages() + ", current page: " + games.getNumber());
			
			return mapper.writeValueAsString(games);
		}
		
		throw new IllegalArgumentException("Unsupported tool function: " + toolRequest.getFunctionName());
	}
}

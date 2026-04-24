package com.shade.videogame.gaming_api.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shade.videogame.gaming_api.model.common.ChatMessage;
import com.shade.videogame.gaming_api.service.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    Logger logger = Logger.getLogger(ChatController.class.getName());

    @Autowired
    private ChatService chatService;

    @PostMapping
    public List<ChatMessage> chat(@RequestBody List<ChatMessage> messages) throws IOException {
        logger.info("Received [" + messages + " ] in request body");
        
        // Return the message from the chat.
        List<ChatMessage> botMessages = chatService.chat(messages);
        return botMessages;
    }

}
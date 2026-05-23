package ru.kpfu.itis.mukminov.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.kpfu.itis.mukminov.dto.ChatMessageDto;
import ru.kpfu.itis.mukminov.service.ChatMessageService;

import java.security.Principal;

@Controller
public class ChatMessageHandler {

    private final ChatMessageService chatMessageService;

    public ChatMessageHandler(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessageDto handleMessage(String content, Principal principal) {
        return chatMessageService.saveMessage(content, principal.getName());
    }
}
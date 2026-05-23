package ru.kpfu.itis.mukminov.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.mukminov.dto.ChatMessageDto;
import ru.kpfu.itis.mukminov.service.ChatMessageService;

import java.util.List;

@RestController
@RequestMapping("/admin/messages")
public class AdminChatController {

    private final ChatMessageService chatMessageService;

    public AdminChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping
    public List<ChatMessageDto> getAllMessages() {
        return chatMessageService.getAllMessagesForAdmin();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        chatMessageService.deleteMessageByAdmin(id);
        return ResponseEntity.ok().build();
    }
}
package ru.kpfu.itis.mukminov.service;

import ru.kpfu.itis.mukminov.dto.ChatMessageDto;
import ru.kpfu.itis.mukminov.model.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    List<ChatMessage> getRecentMessages();
    List<ChatMessage> getMessagesByUser(String email);
    ChatMessageDto saveMessage(String content, String email);
    void deleteMessageByUser(Long messageId, String email);
    List<ChatMessageDto> getAllMessagesForAdmin();
    void deleteMessageByAdmin(Long messageId);
}
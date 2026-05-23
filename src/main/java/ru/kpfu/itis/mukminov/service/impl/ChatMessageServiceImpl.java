package ru.kpfu.itis.mukminov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.mukminov.dto.ChatMessageDto;
import ru.kpfu.itis.mukminov.model.ChatMessage;
import ru.kpfu.itis.mukminov.model.User;
import ru.kpfu.itis.mukminov.repository.ChatMessageRepository;
import ru.kpfu.itis.mukminov.repository.UserRepository;
import ru.kpfu.itis.mukminov.service.ChatMessageService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository, UserRepository userRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getRecentMessages() {
        List<ChatMessage> messages = chatMessageRepository.findTop50ByOrderBySentAtDesc();
        messages.sort(Comparator.comparing(ChatMessage::getSentAt));
        return messages;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getMessagesByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return chatMessageRepository.findByAuthor(user);
    }

    @Override
    @Transactional
    public ChatMessageDto saveMessage(String content, String email) {
        User author = userRepository.findByEmail(email).orElseThrow();

        ChatMessage message = new ChatMessage();
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());
        message.setAuthor(author);
        chatMessageRepository.save(message);

        return new ChatMessageDto(
                message.getId(),
                message.getContent(),
                author.getName() + " " + author.getLastname(),
                message.getSentAt()
        );
    }

    @Override
    @Transactional
    public void deleteMessageByUser(Long messageId, String email) {
        ChatMessage message = chatMessageRepository.findById(messageId).orElseThrow();
        User currentUser = userRepository.findByEmail(email).orElseThrow();

        if (message.getAuthor().getId().equals(currentUser.getId())) {
            chatMessageRepository.delete(message);
        } else {
            throw new SecurityException("Вы не можете удалить чужое сообщение");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageDto> getAllMessagesForAdmin() {
        return chatMessageRepository.findAll().stream()
                .map(m -> new ChatMessageDto(
                        m.getId(),
                        m.getContent(),
                        m.getAuthor().getEmail(),
                        m.getSentAt()
                )).toList();
    }

    @Override
    @Transactional
    public void deleteMessageByAdmin(Long messageId) {
        chatMessageRepository.deleteById(messageId);
    }
}
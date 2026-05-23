package ru.kpfu.itis.mukminov.dto;

import java.time.LocalDateTime;

public record ChatMessageDto(Long id, String content, String authorName, LocalDateTime sentAt) {
}
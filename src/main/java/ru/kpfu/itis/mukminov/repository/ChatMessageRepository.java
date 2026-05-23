package ru.kpfu.itis.mukminov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.mukminov.model.ChatMessage;
import ru.kpfu.itis.mukminov.model.User;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findTop50ByOrderBySentAtDesc();
    
    List<ChatMessage> findByAuthor(User author);

    @Query("SELECT c FROM ChatMessage c WHERE LOWER(c.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ChatMessage> searchByContent(@Param("keyword") String keyword);
}
package ru.kpfu.itis.mukminov.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import ru.kpfu.itis.mukminov.config.properties.MailProperties;
import ru.kpfu.itis.mukminov.model.User;
import ru.kpfu.itis.mukminov.service.impl.EmailService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MailProperties mailProperties;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setName("Ivan");
        testUser.setEmail("ivan@test.com");
        testUser.setVerificationCode("test-uuid");

        when(mailProperties.baseUrl()).thenReturn("http://localhost:8080");
        when(mailProperties.content()).thenReturn("Hello $name, link: $url");
        when(mailProperties.from()).thenReturn("noreply@test.com");
        when(mailProperties.subject()).thenReturn("Verify your account");
    }

    @Test
    void sendVerificationEmail_Success() {
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendVerificationEmail(testUser);

        verify(mailSender, times(1)).send(mimeMessage);
    }
}
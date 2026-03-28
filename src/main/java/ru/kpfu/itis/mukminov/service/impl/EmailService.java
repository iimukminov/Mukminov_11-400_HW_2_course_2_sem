package ru.kpfu.itis.mukminov.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.mukminov.config.properties.MailProperties;
import ru.kpfu.itis.mukminov.model.User;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    public EmailService(JavaMailSender mailSender, MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
    }

    public void sendVerificationEmail(User user) {
        String url = mailProperties.baseUrl() + "/verification?code=" + user.getVerificationCode();
        String content = mailProperties.content()
                .replace("$name", user.getName())
                .replace("$url", url);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setFrom(mailProperties.from());
            helper.setTo(user.getEmail());
            helper.setSubject(mailProperties.subject());
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
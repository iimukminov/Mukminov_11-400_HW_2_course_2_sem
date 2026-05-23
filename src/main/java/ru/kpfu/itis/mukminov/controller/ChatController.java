package ru.kpfu.itis.mukminov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.mukminov.service.ChatMessageService;

import java.security.Principal;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatMessageService chatMessageService;

    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping
    public String chatPage(Model model) {
        model.addAttribute("messages", chatMessageService.getRecentMessages());
        return "chat";
    }

    @GetMapping("/public")
    public String publicChat(Model model) {
        model.addAttribute("messages", chatMessageService.getRecentMessages());
        return "public_chat";
    }

    @GetMapping("/my")
    public String myMessages(Model model, Principal principal) {
        model.addAttribute("messages", chatMessageService.getMessagesByUser(principal.getName()));
        return "my_messages";
    }

    @PostMapping("/{id}/delete")
    public String deleteMyMessage(@PathVariable Long id, Principal principal) {
        chatMessageService.deleteMessageByUser(id, principal.getName());
        return "redirect:/chat/my";
    }
}
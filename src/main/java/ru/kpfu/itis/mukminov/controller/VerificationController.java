package ru.kpfu.itis.mukminov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.mukminov.service.UserService;

@Controller
public class VerificationController {

    private final UserService userService;

    public VerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verification")
    public String verify(@RequestParam("code") String code) {
        boolean isVerified = userService.verifyUser(code);
        if (isVerified) {
            return "redirect:/login?verified=true"; 
        } else {
            return "redirect:/login?error=invalid_code";
        }
    }
}
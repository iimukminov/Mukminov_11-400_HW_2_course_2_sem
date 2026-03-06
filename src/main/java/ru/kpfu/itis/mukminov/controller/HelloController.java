package ru.kpfu.itis.mukminov.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.mukminov.model.User;
import ru.kpfu.itis.mukminov.repository.UserRepository;
import ru.kpfu.itis.mukminov.service.HelloService;

import java.util.List;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "da", required = false) String name) {
        return helloService.sayHello(name);
    }
}


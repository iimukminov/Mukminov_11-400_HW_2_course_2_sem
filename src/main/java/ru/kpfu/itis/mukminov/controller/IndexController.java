package ru.kpfu.itis.mukminov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.mukminov.aop.Metrics;

@Controller
public class IndexController {

    @Metrics
    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }
}

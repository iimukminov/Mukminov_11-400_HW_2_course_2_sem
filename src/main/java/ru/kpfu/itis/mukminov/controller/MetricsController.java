package ru.kpfu.itis.mukminov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.mukminov.aop.Metrics;
import ru.kpfu.itis.mukminov.service.impl.MetricsServiceImpl;

import java.util.Map;

@Controller
@RequestMapping("/metrics")
public class MetricsController {

    private final MetricsServiceImpl metricsService;

    public MetricsController(MetricsServiceImpl metricsService) {
        this.metricsService = metricsService;
    }

    @Metrics
    @GetMapping
    public String getMetrics(Model model) {
        model.addAttribute("metrics", metricsService.getAllMetrics());
        return "metrics";
    }

    @Metrics
    @GetMapping(params = "methodName")
    public String searchMetrics(@RequestParam(name = "methodName", required = false) String methodName, Model model) {
        model.addAttribute("metrics", Map.of(methodName, metricsService.getMetrics(methodName)));
        model.addAttribute("searchMethod", methodName);
        return "metrics";
    }
}

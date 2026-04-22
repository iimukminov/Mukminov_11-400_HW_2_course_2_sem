package ru.kpfu.itis.mukminov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.mukminov.aop.Benchmark;
import ru.kpfu.itis.mukminov.service.impl.BenchmarkServiceImpl;

@Controller
@RequestMapping("/benchmarks")
public class BenchmarkController {

    private final BenchmarkServiceImpl benchmarkService;

    public BenchmarkController(BenchmarkServiceImpl benchmarkService) {
        this.benchmarkService = benchmarkService;
    }

    @Benchmark
    @GetMapping
    public String getAllBenchmarks(Model model) {
        model.addAttribute("benchmarks", benchmarkService.getAllBenchmarks());
        return "benchmarks";
    }

    @Benchmark
    @GetMapping(params = {"methodName", "n"})
    public String calculatePercentile(@RequestParam("methodName") String methodName, @RequestParam("n") double n, Model model) {

        model.addAttribute("selectedMethod", methodName);
        model.addAttribute("nValue", n);

        Double result = benchmarkService.calculatePercentile(methodName, n);

        if (result == null) {
            model.addAttribute("errorMessage", "Нет данных для метода: " + methodName);
        } else {
            model.addAttribute("percentileResult", String.format("%.2f мс", result));
        }

        return "benchmarks";
    }

}

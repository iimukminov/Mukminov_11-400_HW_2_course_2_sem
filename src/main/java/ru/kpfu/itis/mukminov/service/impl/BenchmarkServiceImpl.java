package ru.kpfu.itis.mukminov.service.impl;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.mukminov.repository.BenchmarkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BenchmarkServiceImpl {

    private final BenchmarkRepository benchmarkRepository;

    public BenchmarkServiceImpl(BenchmarkRepository benchmarkRepository) {
        this.benchmarkRepository = benchmarkRepository;
    }

    public void addExecutionTime(String methodName, long executionTime) {
        benchmarkRepository.addExecutionTime(methodName, executionTime);
    }

    public List<Long> getExecutionTimes(String methodName) {
        return benchmarkRepository.getExecutionTimes(methodName);
    }

    public Map<String, List<Long>> getAllBenchmarks() {
        return benchmarkRepository.getAllBenchmarks();
    }

    public Double calculatePercentile(String methodName, double n) {
        List<Long> times = benchmarkRepository.getExecutionTimes(methodName);

        if (times == null || times.isEmpty()) {
            return null;
        }

        double[] timesArray = times.stream()
                .mapToDouble(Long::doubleValue)
                .toArray();

        return new Percentile().evaluate(timesArray, n);
    }
}

package ru.kpfu.itis.mukminov.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BenchmarkRepository {

    private final ConcurrentHashMap<String, List<Long>> benchmarks = new ConcurrentHashMap<>();

    public void addExecutionTime(String methodName, long executionTime) {
        benchmarks.computeIfAbsent(methodName, k -> new ArrayList<>()).add(executionTime);
    }

    public List<Long> getExecutionTimes(String methodName) {
        return benchmarks.get(methodName);
    }

    public Map<String, List<Long>> getAllBenchmarks() {
        return benchmarks;
    }
}

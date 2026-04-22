package ru.kpfu.itis.mukminov.repository;

import org.springframework.stereotype.Repository;
import ru.kpfu.itis.mukminov.dto.MetricsInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MetricsRepository {

    private final ConcurrentHashMap<String, MetricsInfo> metrics = new ConcurrentHashMap<>();

    public void incrementSuccess(String methodName) {
        metrics.computeIfAbsent(methodName, k -> new MetricsInfo()).incrementSuccess();
    }

    public void incrementFailure(String methodName) {
        metrics.computeIfAbsent(methodName, k -> new MetricsInfo()).incrementFailure();
    }

    public MetricsInfo getMetricsByName(String methodName) {
        return metrics.getOrDefault(methodName,  new MetricsInfo());
    }

    public Map<String, MetricsInfo> getAllMetrics() {
        return metrics;
    }
 }

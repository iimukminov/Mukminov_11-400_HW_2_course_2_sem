package ru.kpfu.itis.mukminov.service.impl;

import org.springframework.stereotype.Service;
import ru.kpfu.itis.mukminov.dto.MetricsInfo;
import ru.kpfu.itis.mukminov.repository.MetricsRepository;

import java.util.Map;

@Service
public class MetricsServiceImpl {

    private final MetricsRepository metricsRepository;

    public MetricsServiceImpl(MetricsRepository metricsRepository) {
        this.metricsRepository = metricsRepository;
    }

    public MetricsInfo getMetrics(String methodName) {
        return metricsRepository.getMetricsByName(methodName);
    }

    public Map<String, MetricsInfo> getAllMetrics() {
        return metricsRepository.getAllMetrics();
    }

    public void incrementSuccess(String methodName) {
        metricsRepository.incrementSuccess(methodName);
    }

    public void incrementFailure(String methodName) {
        metricsRepository.incrementFailure(methodName);
    }


}

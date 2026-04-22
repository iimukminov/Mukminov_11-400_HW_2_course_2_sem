package ru.kpfu.itis.mukminov.dto;

import java.util.concurrent.atomic.AtomicInteger;

public class MetricsInfo {
    private static final AtomicInteger successCount = new AtomicInteger(0);
    private static final AtomicInteger failureCount = new AtomicInteger(0);

    public void incrementSuccess() {
        successCount.incrementAndGet();
    }

    public void incrementFailure() {
        failureCount.incrementAndGet();
    }

    public int getSuccessCount() {
        return successCount.get();
    }

    public int getFailureCount() {
        return failureCount.get();
    }
}
package ru.kpfu.itis.mukminov.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.mukminov.service.impl.BenchmarkServiceImpl;
import ru.kpfu.itis.mukminov.service.impl.MetricsServiceImpl;

@Aspect
@Component
public class MonitoringAspect {

    private final MetricsServiceImpl metricsService;
    private final BenchmarkServiceImpl benchmarkService;

    public MonitoringAspect(MetricsServiceImpl metricsService, BenchmarkServiceImpl benchmarkService) {
        this.metricsService = metricsService;
        this.benchmarkService = benchmarkService;
    }

    @Pointcut("@annotation(Metrics)")
    public void metricsAnnotated() {
    }

    @Around("metricsAnnotated()")
    public Object metricsAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        String fullMethodName = className + "." + methodName;
        try {
            Object result = joinPoint.proceed();
            metricsService.incrementSuccess(fullMethodName);
            return result;
        } catch (Throwable throwable) {
            metricsService.incrementFailure(fullMethodName);
            throw throwable;
        }
    }

    @Pointcut("@annotation(Benchmark)")
    public void benchmarkAnnotated() {
    }

    @Around("benchmarkAnnotated()")
    public Object benchmarkAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        String fullMethodName = className + "." + methodName;

        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            benchmarkService.addExecutionTime(fullMethodName, System.currentTimeMillis() - start);
        }
    }
}

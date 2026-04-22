package ru.kpfu.itis.mukminov.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.mukminov.service.impl.MetricsServiceImpl;

@Aspect
@Component
public class MonitoringAspect {

    private final MetricsServiceImpl metricsService;

    public MonitoringAspect(MetricsServiceImpl metricsService) {
        this.metricsService = metricsService;
    }

    @Pointcut("@annotation(Metrics)")
    public void metricsAnnotated() {
    }

    @Around("metricsAnnotated()")
    public Object metricsAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();

        try {
            Object result = joinPoint.proceed();
            metricsService.incrementSuccess(methodName);
            return result;
        } catch (Throwable throwable) {
            metricsService.incrementFailure(methodName);
            throw throwable;
        }
    }
}

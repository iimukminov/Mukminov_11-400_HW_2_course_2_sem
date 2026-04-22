package ru.kpfu.itis.mukminov.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution (* ru.kpfu.itis.mukminov..*.*(..)) " +
            "&& !within(ru.kpfu.itis.mukminov.dto..*) " +
            "&& !within(ru.kpfu.itis.mukminov.model..*) " +
            "&& !within(ru.kpfu.itis.mukminov.config..*)")
    public void logExecution() {
    }

    @Pointcut("@annotation(Loggable)")
    public void logAnnotated() {}

    @Around("logExecution()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        LOGGER.info("Start execution {}.{}", className, methodName);
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException();
        }

        LOGGER.info("End execution {}.{}", className, methodName);
        return result;
    }
}

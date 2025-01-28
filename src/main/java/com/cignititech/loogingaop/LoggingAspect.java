package com.cignititech.loogingaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut to target all methods in the controller and service packages
    @Pointcut("within(com.cignititech.controller..*) || within(com.cignititech.service..*)")
    public void applicationPackagePointcut() {
    }

    // Log method entry, execution time, and exit
    @Around("applicationPackagePointcut()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String correlationId = MDC.get("correlationId");

        // Log method entry
        log.info("[Correlation ID: {}] Entering method: {} with arguments: {}",
                correlationId,
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));

        Object result;
        try {
            // Proceed with method execution
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            // Log exceptions
            log.error("[Correlation ID: {}] Exception in method: {} with cause: {}",
                    correlationId,
                    joinPoint.getSignature().toShortString(),
                    throwable.getMessage() != null ? throwable.getMessage() : "NULL");
            throw throwable;
        }

        // Log method exit and execution time
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("[Correlation ID: {}] Exiting method: {} with result: {}. Execution time: {} ms",
                correlationId,
                joinPoint.getSignature().toShortString(),
                result,
                executionTime);

        return result;
    }
}
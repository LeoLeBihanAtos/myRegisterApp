package com.example.myRegisterApp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution time and method calls.
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs method execution time and parameters.
     *
     * @param joinPoint the join point representing the method execution
     * @return the result of the method execution
     * @throws Throwable if an error occurs during method execution
     */
    @Around("execution(* com.example.myRegisterApp.*.*(..)) || execution(* com.example.myRegisterApp.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result;
        try {
            logger.info("➡️ Call method: {} with parameters: {}", joinPoint.getSignature(), joinPoint.getArgs());

            result = joinPoint.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            logger.info("✅ Method {} executed in {} ms", joinPoint.getSignature(), duration);
        }

        logger.info("⬅️ Return of {} method with result: {}", joinPoint.getSignature(), result);
        return result;
    }
}
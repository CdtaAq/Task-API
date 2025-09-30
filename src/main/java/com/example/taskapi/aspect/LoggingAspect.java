package com.example.taskapi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut: public methods in service and controller packages (adjust as needed)
    @Around("execution(* com.example.taskapi..*(..)) && (within(com.example.taskapi.service..*) || within(com.example.taskapi.controller..*))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object retval = joinPoint.proceed();
        long time = System.currentTimeMillis() - start;
        String methodName = joinPoint.getSignature().getName();
        log.info("[LOG] Method {} executed in {} ms", methodName, time);
        return retval;
    }
}

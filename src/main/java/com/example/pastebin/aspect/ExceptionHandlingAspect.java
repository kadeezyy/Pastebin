package com.example.pastebin.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionHandlingAspect {
    @Pointcut(value = "@annotation(com.example.pastebin.aspect.ExceptionThrown)")
    public void isAnnotatedWithExceptionThrown() {
    }

    @Around("isAnnotatedWithExceptionThrown() && target(service)")
    public Object addExceptionHandlingAfterThrown(ProceedingJoinPoint joinPoint, Object service) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            log.info("Result returning from proceedingJoinPoint {}", result);
            return result;
        } catch (Throwable ex) {
            log.info("Got exception in class: {}, exception: {}", service, ex.getMessage());
            throw ex;
        } finally {
            log.info("Proceeding further");
        }
    }
}

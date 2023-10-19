package com.example.pastebin.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ExceptionHandlingAspect {
    @Pointcut(value = "@annotation(com.example.pastebin.aop.aspect.Logged)")
    public void isAnnotatedWithLogging() {
    }

    @Around("isAnnotatedWithLogging() && target(service)")
    public Object addExceptionHandlingAfterThrown(ProceedingJoinPoint joinPoint, Object service) throws Throwable {
        Object[] methodArgs = joinPoint.getArgs();
        try {
            log.info("Method receiving these parameters: {}", Arrays.asList(methodArgs));
            Object result = joinPoint.proceed();
            log.info("Result returning from proceedingJoinPoint {}", result);
            return result;
        } catch (Throwable ex) {
            log.info("Got exception in class: {}, exception: {}", service, ex.getMessage());
            throw ex;
        }
    }
}

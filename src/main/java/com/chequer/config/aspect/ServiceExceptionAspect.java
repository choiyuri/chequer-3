package com.chequer.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServiceExceptionAspect {

    @Around("execution(* com.chequer.service.*.*(..))")
    public Object serviceExceptionHandler(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            // 서비스 로직에서 에러 발생 시 RuntimeException 발생
            throw new RuntimeException(e);
        }
    }
}

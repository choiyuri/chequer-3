package com.chequer.config.aspect;

import com.chequer.web.RestResponse;
import com.chequer.web.ResultType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RestControllerAspect {

    @Around("execution(* com.chequer.web.controller.*.*(..))")
    public RestResponse restResponseHandle(ProceedingJoinPoint joinPoint) throws Throwable {
        return RestResponse.builder()
                .result(ResultType.SUCCESS)
                .data(joinPoint.proceed())
                .build();
    }

}

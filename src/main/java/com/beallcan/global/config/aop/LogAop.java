package com.beallcan.global.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAop {

    // controller와 service method만 로그 적용
    @Around("execution(* com.beallcan.domain..controller..*(..)) || execution(* com.beallcan.domain..service..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("START: {}", joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            log.info("END: {}", joinPoint.toString());
        }
    }
}

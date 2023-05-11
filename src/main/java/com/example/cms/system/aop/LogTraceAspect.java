package com.example.cms.system.aop;

import com.example.cms.system.aop.logtrace.LogTrace;
import com.example.cms.system.aop.logtrace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 실행 로그 추적기
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }


    /**
     * 1. 정의된 Pointcut 에서 실행 <br>
     * 2. `@ExcludeTrace` 어노테이션이 붙어 있는 메서드는 제외
     *
     * @see com.example.cms.system.aop.pointcut.Pointcuts
     * @see com.example.cms.system.annotation.ExcludeTrace
     */
    @Around("com.example.cms.system.aop.pointcut.Pointcuts.controllerServiceAndTrace()")
    public Object doTimer(ProceedingJoinPoint joinPoint) throws Throwable {

        TraceStatus status = null;
        try {

            status = logTrace.begin(joinPoint.getSignature().toShortString());

            // 로직 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);

            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}

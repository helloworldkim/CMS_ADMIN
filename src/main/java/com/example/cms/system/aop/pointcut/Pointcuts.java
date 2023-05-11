package com.example.cms.system.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Pointcuts
 */
public class Pointcuts {

    @Pointcut("@annotation(com.example.cms.system.annotation.LogTrace)")
    public void logTraceAnnotation() {
    }

    @Pointcut("@annotation(com.example.cms.system.annotation.ExcludeTrace)")
    public void excludeTraceAnnotation() {
    }

    @Pointcut("execution(* com.example.cms.domain..*Repository.*(..))")
    public void allRepository() {
    }

    @Pointcut("execution(* com.example.cms.domain..*Service.*(..))")
    public void allService() {
    }

    @Pointcut("execution(* com.example.cms.domain..*Controller.*(..))")
    public void allController() {
    }

    @Pointcut("allController() || allService()")
    public void controllerService() {
    }

    @Pointcut("allController() || allService() || allRepository()")
    public void controllerServiceRepository() {
    }

    @Pointcut("(allController() || allService() || logTraceAnnotation()) && !excludeTraceAnnotation()")
    public void controllerServiceAndTrace() {
    }
}

package com.example.cms.system.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LogTraceAspect 제외 하기 위한 어노테이션
 * <p>
 * {@link com.example.cms.system.aop.LogTraceAspect} 적용 제외 어노테이션
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeTrace {

}

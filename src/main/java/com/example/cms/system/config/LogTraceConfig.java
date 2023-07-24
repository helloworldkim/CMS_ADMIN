package com.example.cms.system.config;

import com.example.cms.system.aop.logtrace.LogTrace;
import com.example.cms.system.aop.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

}

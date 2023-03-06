package com.example.cms.system.config;

import com.example.cms.system.interceptor.AuthInterceptor;
import com.example.cms.system.interceptor.InfoInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final InfoInterceptor infoInterceptor;
    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> interceptorExcludeList = Arrays.asList(
                                                            "/css/**",
                                                            "/js/**",
                                                            "/images/**",
                                                            "/error/**",
                                                            "/download/**",
                                                            "/common/file**",
                                                            "/*.ico");

        registry.addInterceptor(infoInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(interceptorExcludeList);

        registry.addInterceptor(authInterceptor)
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(interceptorExcludeList);
    }
}

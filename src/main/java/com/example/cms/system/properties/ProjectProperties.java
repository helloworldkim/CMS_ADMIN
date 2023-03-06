package com.example.cms.system.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "project")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ProjectProperties {
    private String nameKor;
    private String name;
    private String service;
    private String alias;
    /**
     * 프로젝트 시스템 속성
     */
    private final System system;


}

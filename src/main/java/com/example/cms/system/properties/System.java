package com.example.cms.system.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@ToString
@Component
public class System {
    // 관리자 사이트 URL
    private String       adminSiteUrl;
    // 관리자 인증 + 권한 체크가 필요 없는 URL 목록
    private List<String> adminPermitAllUrl;
    // 관리자 인증은 필요하나 권한 체크가 필요 없는 URL 목록
    private List<String> adminPermitAuthUrl;
    // 업로드 위치
    private String       uploadPath;
}

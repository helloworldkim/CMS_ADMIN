package com.example.cms.domain.authadmin.dto;

import com.example.cms.system.enums.AdminMainAccessType;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class AuthAdminDTO {

    // 관리자 그룹명
    private String adminGroupName;
    // 메인 메뉴 목록
//    private List<AdminMenuResVO> mainMenuList;
    // 서브 메뉴 목록
//    private Map<Integer, List<AdminMenuResVO>> subMenuMap;
    // 권한 정보
//    private Map<String, AdminMenuResVO>        authMap;
    // HOME URL
    private String homeUrl;
    // 메인 최초 접속 여부
    private AdminMainAccessType adminMainAccessType;

    /**
     * 계정 ID
     */
    private String adminId;
    private Long adminGroupId;
    /**
     * 이름 - update 시 필수
     */
    private String adminName;
    /**
     * 이메일
     */
    private String email;

    @Builder
    public AuthAdminDTO(String adminGroupName, String homeUrl, AdminMainAccessType adminMainAccessType, String adminId, Long adminGroupId, String adminName, String email) {
        this.adminGroupName = adminGroupName;
        this.homeUrl = homeUrl;
        this.adminMainAccessType = adminMainAccessType;
        this.adminId = adminId;
        this.adminGroupId = adminGroupId;
        this.adminName = adminName;
        this.email = email;
    }
}

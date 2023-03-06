package com.example.cms.domain.authadmin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class AuthAdminDTO {

    // 관리자 그룹명
    private String                               adminGroupName;
    // 메인 메뉴 목록
//    private List<AdminMenuResVO> mainMenuList;
    // 서브 메뉴 목록
//    private Map<Integer, List<AdminMenuResVO>> subMenuMap;
    // 권한 정보
//    private Map<String, AdminMenuResVO>        authMap;
    // HOME URL
    private String                               homeUrl;
    // 메인 최초 접속 여부
    private String                               mainFirstAccessYn;

    /**
     * 계정 ID
     */
    private String        adminId;
    /**
     * 계정 고유번호
     */
    private String        adminKey;
    /**
     * 비밀번호
     */
    private String        pwd;
    /**
     * 관리자 그룹 순번 - update 시 필수
     */
    private Integer       adminGroupSeq;
    /**
     * 이름 - update 시 필수
     */
    private String        adminName;
    /**
     * 이메일
     */
    private String        email;
}

package com.example.cms.domain.authadmin.dto;

import com.example.cms.domain.adminmenugroup.dto.AdminGroupMenuDTO;
import com.example.cms.system.enums.AdminMainAccessType;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class AuthAdminDTO {
    String adminGroupName;
    List<AdminGroupMenuDTO> mainMenuList;
    Map<Integer, List<AdminGroupMenuDTO>> subMenuMap;
    Map<String, AdminGroupMenuDTO> authMap;
    String homeUrl;
    AdminMainAccessType adminMainAccessType;
    String adminId;
    Long adminGroupId;
    String adminName;
    String email;
    @Builder
    public AuthAdminDTO(String adminGroupName, List<AdminGroupMenuDTO> mainMenuList, Map<Integer, List<AdminGroupMenuDTO>> subMenuMap, Map<String, AdminGroupMenuDTO> authMap, String homeUrl, AdminMainAccessType adminMainAccessType, String adminId, Long adminGroupId, String adminName, String email) {
        this.adminGroupName = adminGroupName;
        this.mainMenuList = mainMenuList;
        this.subMenuMap = subMenuMap;
        this.authMap = authMap;
        this.homeUrl = homeUrl;
        this.adminMainAccessType = adminMainAccessType;
        this.adminId = adminId;
        this.adminGroupId = adminGroupId;
        this.adminName = adminName;
        this.email = email;
    }
}

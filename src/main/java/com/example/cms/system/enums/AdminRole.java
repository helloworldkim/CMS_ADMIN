package com.example.cms.system.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
public enum AdminRole {
    //시스템 계정, 일반 관리자, 부 관리자
    ROLE_MASTER("시스템어드민")
    , ROLE_ADMIN("일반관리자")
    , ROLE_SUB_ADMIN("부관리자");

    private final String displayName;

    public static AdminRole findAdminRole(String adminRoleStr) {
        return Arrays.stream(AdminRole.values())
                .filter(adminRole -> adminRole.name().equals(adminRoleStr))
                .findFirst()
                .orElseThrow();
    }

    AdminRole(String displayName) {
        this.displayName = displayName;
    }
}

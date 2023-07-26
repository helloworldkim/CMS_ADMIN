package com.example.cms.system.enums;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminRoleTest {

    @Test
    @DisplayName("enum값으로 대상을 찾을 수 있다.(ROLE_MASTER)")
    void findAdminRole1() {
        //given
        String adminRoleStr = "ROLE_MASTER";
        AdminRole adminRole = AdminRole.findAdminRole(adminRoleStr);

        //when then
        Assertions.assertThat(adminRole).isEqualTo(AdminRole.ROLE_MASTER);
    }
    @Test
    @DisplayName("enum값으로 대상을 찾을 수 있다.(ROLE_ADMIN)")
    void findAdminRole2() {
        //given
        String adminRoleStr = "ROLE_ADMIN";
        AdminRole adminRole = AdminRole.findAdminRole(adminRoleStr);

        //when then
        Assertions.assertThat(adminRole).isEqualTo(AdminRole.ROLE_ADMIN);
    }
    @Test
    @DisplayName("enum값으로 대상을 찾을 수 있다.(ROLE_SUB_ADMIN)")
    void findAdminRole3() {
        //given
        String adminRoleStr = "ROLE_SUB_ADMIN";
        AdminRole adminRole = AdminRole.findAdminRole(adminRoleStr);

        //when then
        Assertions.assertThat(adminRole).isEqualTo(AdminRole.ROLE_SUB_ADMIN);
    }

    @Test
    @DisplayName("enum의 각각 설명을 가져올 수 있다.(ROLE_MASTER)")
    void getDisplayName1() {
        //given
        String displayName = AdminRole.ROLE_MASTER.getDisplayName();

        //when then
        Assertions.assertThat(displayName).isEqualTo("시스템어드민");
    }
    @Test
    @DisplayName("enum의 각각 설명을 가져올 수 있다.(ROLE_ADMIN)")
    void getDisplayName2() {
        //given
        String displayName = AdminRole.ROLE_ADMIN.getDisplayName();

        //when then
        Assertions.assertThat(displayName).isEqualTo("일반관리자");
    }
    @Test
    @DisplayName("enum의 각각 설명을 가져올 수 있다.(ROLE_SUB_ADMIN)")
    void getDisplayName3() {
        //given
        String displayName = AdminRole.ROLE_SUB_ADMIN.getDisplayName();

        //when then
        Assertions.assertThat(displayName).isEqualTo("부관리자");
    }
}
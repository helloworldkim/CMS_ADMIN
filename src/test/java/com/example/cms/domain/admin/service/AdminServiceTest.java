package com.example.cms.domain.admin.service;

import com.example.cms.domain.admin.dto.AdminDTO;
import com.example.cms.domain.admin.entity.Admin;
import com.example.cms.domain.admin.repository.AdminRepository;
import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.admingroup.repository.AdminGroupRepository;
import com.example.cms.system.enums.AdminMainAccessType;
import com.example.cms.system.enums.AdminRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AdminServiceTest {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AdminService adminService;
    @Autowired
    AdminGroupRepository adminGroupRepository;

    private AdminGroup initAdminGroup;

    /**
     * 어드민은 모두 어드민 그룹을 기본적으로 가지고 있어야 한다.
     */
    @BeforeEach
    void setUp() {
        AdminGroup adminGroup = AdminGroup.builder()
                .name("테스트 그룹")
                .description("어드민그룹 기본셋팅")
                .accessType(AdminMainAccessType.MAIN)
                .build();
        this.initAdminGroup = adminGroupRepository.save(adminGroup);
    }

    @Test
    @DisplayName("회원등록은 어드민 그룹이 있다면 저장가능하다.")
    void adminSave() {
        //given
        Admin admin = Admin.builder()
                .adminId("system")
                .password("test")
                .adminName("테스트유저")
                .adminRole(AdminRole.ROLE_MASTER)
                .adminGroup(initAdminGroup)
                .build();
        //when
        Long id = adminService.save(admin);
        Admin savedAdmin = adminRepository.findById(id).orElseThrow();
        //then
        assertThat(admin).isEqualTo(savedAdmin);
    }
    @Test
    @DisplayName("어드민은 단건 삭제가 가능하다.")
    void adminDelete() {
        //given
        Admin admin = Admin.builder()
                .adminId("system")
                .password("test")
                .adminName("테스트유저")
                .adminRole(AdminRole.ROLE_MASTER)
                .adminGroup(initAdminGroup)
                .build();
        Long id = adminService.save(admin);
        //when
        adminService.deleteByAdminId(admin.getAdminId());
        Admin findAdmin = adminRepository.findById(id).orElseThrow();
        //then
        assertThat(findAdmin.isDeleted()).isTrue();

    }
    @Test
    @DisplayName("어드민은 고유값으로 단건 조회가 가능하다.")
    void adminFindById() {
        //given
        Admin admin = Admin.builder()
                .adminId("system")
                .password("test")
                .adminName("테스트유저")
                .adminRole(AdminRole.ROLE_MASTER)
                .adminGroup(initAdminGroup)
                .build();
        Long id = adminService.save(admin);

        //when
        Admin findAdmin = adminService.findById(id).orElseThrow();

        //then
        assertThat(admin).isEqualTo(findAdmin);

    }
    @Test
    @DisplayName("어드민은 아이디로 단건 조회가 가능하다.")
    void adminFindByAdminId() {
        //given
        Admin admin = Admin.builder()
                .adminId("system")
                .password("test")
                .adminName("테스트유저")
                .adminRole(AdminRole.ROLE_MASTER)
                .adminGroup(initAdminGroup)
                .build();
        adminService.save(admin);

        //when
        Admin findAdmin = adminService.findByAdminId(admin.getAdminId()).orElseThrow();

        //then
        assertThat(admin).isEqualTo(findAdmin);
    }
    @Test
    @DisplayName("어드민 목록 여러건 조회가 가능하다.")
    void adminFindAll() {
        //given
        Admin admin1 = Admin.builder()
                .adminId("test1")
                .password("test1")
                .adminName("테스트유저1")
                .adminRole(AdminRole.ROLE_MASTER)
                .adminGroup(initAdminGroup)
                .build();
        adminService.save(admin1);

        Admin admin2 = Admin.builder()
                .adminId("test2")
                .password("test2")
                .adminName("테스트유저2")
                .adminRole(AdminRole.ROLE_MASTER)
                .adminGroup(initAdminGroup)
                .build();
        adminService.save(admin2);

        //when
        PageRequest pageable = PageRequest.of(0,2); //0,2건
        Page<AdminDTO> adminList = adminService.findAllWithPage(pageable);

        //then
        Assertions.assertThat(adminList.getContent()).hasSize(2);
        assertThat(adminList.getTotalPages()).isEqualTo(1);
    }

}
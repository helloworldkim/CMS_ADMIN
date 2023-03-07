package com.example.cms.domain.admin.service;

import com.example.cms.domain.admin.emun.AdminRole;
import com.example.cms.domain.admin.entity.Admin;
import com.example.cms.domain.admin.repository.AdminRepository;
import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.admingroup.enums.AdminMainAccessType;
import com.example.cms.domain.admingroup.repository.AdminGroupRepository;
import com.example.cms.system.config.QueryDslConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@Import(QueryDslConfig.class)
@ActiveProfiles("test")
class AdminServiceTest {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AdminGroupRepository adminGroupRepository;
    @PersistenceContext
    EntityManager em;

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
        em.flush();
    }

    @Test
    @DisplayName("회원등록 테스트")
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
        Admin savedAdmin = adminRepository.save(admin);

        //then
        assertThat(admin).isEqualTo(savedAdmin);
    }
    @Test
    @DisplayName("회원삭제 테스트")
    void adminDelete() {
        //given
        Admin admin = Admin.builder()
                .adminId("system")
                .password("test")
                .adminName("테스트유저")
                .adminRole(AdminRole.ROLE_MASTER)
                .adminGroup(initAdminGroup)
                .build();
        Admin savedAdmin = adminRepository.save(admin);
        Long id = savedAdmin.getId();
        adminRepository.deleteById(savedAdmin.getId());
        em.flush();
        em.clear();
        //when
        Admin findAdmin = adminRepository.findById(id).orElseThrow();

        //then
        assertThat(findAdmin.isDeleted()).isTrue();

    }
    @Test
    @DisplayName("회원 pk로 단건 조회 테스트")
    void adminFindById() {
        //given
        Admin admin = Admin.builder()
                .adminId("system")
                .password("test")
                .adminName("테스트유저")
                .adminRole(AdminRole.ROLE_MASTER)
                .adminGroup(initAdminGroup)
                .build();
        adminRepository.save(admin);

        //when
        Admin findAdmin = adminRepository.findById(admin.getId()).orElseThrow();

        //then
        assertThat(admin).isEqualTo(findAdmin);

    }
    @Test
    @DisplayName("회원 아이디로 단건 조회 테스트")
    void adminFindByAdminId() {
        //given
        Admin admin = Admin.builder()
                .adminId("system")
                .password("test")
                .adminName("테스트유저")
                .adminRole(AdminRole.ROLE_MASTER)
                .adminGroup(initAdminGroup)
                .build();
        adminRepository.save(admin);

        //when
        Admin findAdmin = adminRepository.findByAdminId(admin.getAdminId()).orElseThrow();

        //then
        assertThat(admin).isEqualTo(findAdmin);
    }
    @Test
    @DisplayName("회원목록 조회 테스트")
    void adminFindAll() {
        //given
        Admin admin1 = Admin.builder()
                .adminId("test1")
                .password("test1")
                .adminName("테스트유저1")
                .adminRole(AdminRole.ROLE_MASTER)
                .adminGroup(initAdminGroup)
                .build();
        adminRepository.save(admin1);

        Admin admin2 = Admin.builder()
                .adminId("test2")
                .password("test2")
                .adminName("테스트유저2")
                .adminRole(AdminRole.ROLE_MASTER)
                .adminGroup(initAdminGroup)
                .build();
        adminRepository.save(admin2);

        //when
        PageRequest pageable = PageRequest.of(0,2); //0,2건
        Page<Admin> adminList = adminRepository.findAll(pageable);

        //then
        assertThat(adminList.getContent().size()).isEqualTo(2);
        assertThat(adminList.getTotalPages()).isEqualTo(1);
    }

}
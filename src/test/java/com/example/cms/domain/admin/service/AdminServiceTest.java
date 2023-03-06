package com.example.cms.domain.admin.service;

import com.example.cms.domain.admin.emun.AdminRole;
import com.example.cms.domain.admin.entity.Admin;
import com.example.cms.domain.admin.repository.AdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class AdminServiceTest {

    @Autowired
    AdminRepository adminRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("회원등록 테스트")
    void adminSave() {
        //given
        Admin admin = Admin.builder()
                .adminId("system")
                .password("test")
                .adminName("테스트유저")
                .adminRole(AdminRole.ROLE_MASTER)
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
                .build();
        adminRepository.save(admin1);

        Admin admin2 = Admin.builder()
                .adminId("test2")
                .password("test2")
                .adminName("테스트유저2")
                .adminRole(AdminRole.ROLE_MASTER)
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
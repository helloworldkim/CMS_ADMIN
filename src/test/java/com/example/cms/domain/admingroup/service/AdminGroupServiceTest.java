package com.example.cms.domain.admingroup.service;

import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.admingroup.enums.AdminMainAccessType;
import com.example.cms.domain.admingroup.repository.AdminGroupRepository;
import com.example.cms.system.config.QueryDslConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QueryDslConfig.class)
@ActiveProfiles("test")
class AdminGroupServiceTest {

    @Autowired
    private AdminGroupRepository adminGroupRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("어드민 그룹 등록 테스트")
    void save() {
        //given
        AdminGroup adminGroup = AdminGroup.builder()
                .name("테스트 그룹")
                .description("어드민그룹 기본셋팅")
                .accessType(AdminMainAccessType.MAIN)
                .build();

        AdminGroup saveAdminGroup = adminGroupRepository.save(adminGroup);

        //when

        //then
        assertThat(saveAdminGroup).isEqualTo(adminGroup);
    }

    @Test
    @DisplayName("어드민 그룹 단건 조회 테스트")
    void findById() {
        //given
        AdminGroup adminGroup = AdminGroup.builder()
                .name("테스트 그룹")
                .description("어드민그룹 기본셋팅")
                .accessType(AdminMainAccessType.MAIN)
                .build();
        AdminGroup saveAdminGroup = adminGroupRepository.save(adminGroup);

        //when
        AdminGroup findAdminGroup = adminGroupRepository.findById(saveAdminGroup.getAdminGroupId()).orElseThrow();

        //then
        assertThat(findAdminGroup).isEqualTo(saveAdminGroup);
    }

    @Test
    @DisplayName("어드민 그룹 삭제 테스트")
    void delete() {
        //given
        AdminGroup adminGroup = AdminGroup.builder()
                .name("테스트 그룹")
                .description("어드민그룹 기본셋팅")
                .accessType(AdminMainAccessType.MAIN)
                .build();
        AdminGroup saveAdminGroup = adminGroupRepository.save(adminGroup);
        Long adminGroupId = saveAdminGroup.getAdminGroupId();
        //when
        adminGroupRepository.deleteById(saveAdminGroup.getAdminGroupId());
        em.flush();

        //then
        assertThrows(NoSuchElementException.class, () -> adminGroupRepository.findById(adminGroupId).orElseThrow());

    }

    @Test
    @DisplayName("어드민 그룹 목록 조회 테스트")
    void findAll() {
        //given
        AdminGroup adminGroup1 = AdminGroup.builder()
                .name("테스트 그룹")
                .description("어드민그룹 기본셋팅")
                .accessType(AdminMainAccessType.MAIN)
                .build();
        adminGroupRepository.save(adminGroup1);
        AdminGroup adminGroup2 = AdminGroup.builder()
                .name("테스트 그룹")
                .description("어드민그룹 기본셋팅")
                .accessType(AdminMainAccessType.MAIN)
                .build();
        adminGroupRepository.save(adminGroup2);
        em.flush();

        //when
        List<AdminGroup> list = adminGroupRepository.findAll();

        //then
        assertThat(list.size()).isEqualTo(2);
    }

}
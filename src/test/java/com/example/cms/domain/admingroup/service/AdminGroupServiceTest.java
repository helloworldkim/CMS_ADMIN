package com.example.cms.domain.admingroup.service;

import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.admingroup.repository.AdminGroupRepository;
import com.example.cms.system.enums.AdminMainAccessType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AdminGroupServiceTest {

    @Autowired
    private AdminGroupRepository adminGroupRepository;
    @Autowired
    private AdminGroupService adminGroupService;

    @Test
    @DisplayName("어드민 그룹 등록 테스트")
    void save() {
        //given
        AdminGroup adminGroup = AdminGroup.builder()
                .name("테스트 그룹")
                .description("어드민그룹 기본셋팅")
                .accessType(AdminMainAccessType.MAIN)
                .build();

        //when
        Long id = adminGroupService.save(adminGroup);
        AdminGroup findAdminGroup = adminGroupRepository.findById(id).orElseThrow();

        //then
        assertThat(findAdminGroup).isEqualTo(adminGroup);
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
        AdminGroup findAdminGroup = adminGroupService.findById(saveAdminGroup.getId()).orElseThrow();

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
        Long adminGroupId = saveAdminGroup.getId();
        //when
        adminGroupService.deleteById(adminGroupId);

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

        //when
        List<AdminGroup> list = adminGroupService.findAll();

        //then
        assertThat(list.size()).isEqualTo(2);
    }

}
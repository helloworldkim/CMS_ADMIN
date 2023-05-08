package com.example.cms.domain.adminmenugroup.service;

import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.system.enums.AdminMainAccessType;
import com.example.cms.domain.admingroup.repository.AdminGroupRepository;
import com.example.cms.domain.adminmenugroup.entity.AdminGroupMenu;
import com.example.cms.domain.adminmenugroup.repository.AdminGroupMenuRepository;
import com.example.cms.domain.menu.entity.Menu;
import com.example.cms.domain.menu.repository.MenuRepository;
import com.example.cms.system.config.QueryDslConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(QueryDslConfig.class)
@ActiveProfiles("test")
class AdminGroupMenuServiceTest {

    @Autowired
    private AdminGroupRepository adminGroupRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private AdminGroupMenuRepository adminGroupMenuRepository;
    @PersistenceContext
    private EntityManager em;
    private AdminGroup initAdminGroup;
    private List<Menu> menuList;
    @BeforeEach
    void setUp() {
        AdminGroup adminGroup = AdminGroup.builder()
                .name("테스트 그룹")
                .description("어드민그룹 기본셋팅")
                .accessType(AdminMainAccessType.MAIN)
                .build();

        this.initAdminGroup = adminGroupRepository.save(adminGroup);

        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .order(1)
                .build();
        Menu saveMenu1 = menuRepository.save(menu1);
        Menu menu2 = Menu.builder()
                .name("1번메뉴")
                .order(2)
                .url("/abc/test")
                .build();
        em.flush();
        //when
        menu2.setParent(saveMenu1);
        menuRepository.save(menu2);
        em.flush();
        this.menuList = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "listOrder"));
    }


    @Test
    @DisplayName("어드민 그룹 메뉴 등록 테스트")
    void save() {

        //given
        List<AdminGroupMenu> adminGroupMenuList = new ArrayList<>();
        menuList.forEach(menu -> {
            adminGroupMenuList.add(AdminGroupMenu.builder().adminGroup(initAdminGroup).menu(menu).build());
        } );

        //when
        List<AdminGroupMenu> savedAdminGroupMenuList = adminGroupMenuRepository.saveAll(adminGroupMenuList);
        em.flush();

        //then
        List<AdminGroupMenu> findAdminGroupMenuList = adminGroupMenuRepository.findByAdminGroup(initAdminGroup);
        assertThat(findAdminGroupMenuList.size()).isEqualTo(savedAdminGroupMenuList.size());

    }

    @Test
    @DisplayName("어드민 그룹 메뉴 등록 및 접근권한 변경 테스트")
    void saveAndMenuAccessChangeTest() {

        //given
        List<AdminGroupMenu> adminGroupMenuList = new ArrayList<>();
        menuList.forEach(menu -> {
            adminGroupMenuList.add(AdminGroupMenu.builder().adminGroup(initAdminGroup).menu(menu).build());
        } );

        //when
        List<AdminGroupMenu> savedAdminGroupMenuList = adminGroupMenuRepository.saveAll(adminGroupMenuList);
        for (AdminGroupMenu adminGroupMenu : savedAdminGroupMenuList) {
            adminGroupMenu.updateMenuAccessAuth();
        }
        em.flush();

        //then
        List<AdminGroupMenu> findAdminGroupMenuList = adminGroupMenuRepository.findByAdminGroup(initAdminGroup);
        assertThat(findAdminGroupMenuList.get(0).isMenuAccess()).isTrue();
        assertThat(findAdminGroupMenuList.get(1).isMenuAccess()).isTrue();

    }


}
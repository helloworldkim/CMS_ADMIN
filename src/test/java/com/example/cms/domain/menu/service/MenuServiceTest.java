package com.example.cms.domain.menu.service;

import com.example.cms.domain.menu.entity.Menu;
import com.example.cms.domain.menu.repository.MenuRepository;
import com.example.cms.system.config.QueryDslConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QueryDslConfig.class)
@ActiveProfiles("test")
class MenuServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    MenuRepository menuRepository;

    @Test
    @DisplayName("최상위 메뉴 단건 저장테스트")
    void saveOne() {
        //given
        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .listOrder(1)
                .build();
        //when
        Menu saveMenu = menuRepository.save(menu1);

        //then
        assertThat(menu1).isEqualTo(saveMenu);

    }

    @Test
    @DisplayName("상위메뉴가 존재한다면 PathUrl가 있어야한다. builder에서 null체크 됨")
    void saveOneWithNoPathUrl() {
        //given
        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .listOrder(1)
                .build();

        //when
        //then
        assertThrows(NullPointerException.class, () -> Menu.builder()
                .name("1번메뉴")
                .listOrder(2)
                .parent(menu1)
                .build());

    }

    @Test
    @DisplayName("최상위, 그 다음 메뉴 저장테스트")
    void saveTwo() {
        //given
        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .listOrder(1)
                .build();
        Menu saveMenu1 = menuRepository.save(menu1);
        Menu menu2 = Menu.builder()
                .name("1번메뉴")
                .listOrder(2)
                .pathUrl("/abc/test")
                .build();
        em.flush();
        //when
        menu2.setParent(saveMenu1);
        Menu saveMenu2 = menuRepository.save(menu2);
        em.flush();

        //then
        assertThat(saveMenu2.getParent()).isEqualTo(saveMenu1);

    }

    @Test
    @DisplayName("최상위 메뉴 조회 테스트")
    void findOne() {
        //given
        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .listOrder(1)
                .build();
        //when
        Menu saveMenu = menuRepository.save(menu1);
        Menu menu = menuRepository.findById(saveMenu.getId()).orElseThrow();

        //then
        assertThat(menu1).isEqualTo(menu);

    }

    @Test
    @DisplayName("최상위 메뉴 삭제 테스트")
    void deleteById() {
        //given
        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .listOrder(1)
                .build();
        Menu saveMenu = menuRepository.save(menu1);
        Long id = menu1.getId();

        //when
        menuRepository.deleteById(menu1.getId());
        em.flush();
        em.clear();

        //then
        assertThrows(NoSuchElementException.class, () ->menuRepository.findById(id).orElseThrow());
    }

    @Test
    @DisplayName("최상위 메뉴 삭제 시 하위메뉴도 삭제되어야 한다.")
    void deleteByRoot() {
        //given
        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .listOrder(1)
                .build();
        Menu saveMenu = menuRepository.save(menu1);
        Long id = menu1.getId();

        //when
        menuRepository.deleteById(menu1.getId());
        em.flush();
        em.clear();

        //then
        assertThrows(NoSuchElementException.class, () ->menuRepository.findById(id).orElseThrow());
    }
}
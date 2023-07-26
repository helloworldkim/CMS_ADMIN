package com.example.cms.domain.menu.service;

import com.example.cms.domain.menu.entity.Menu;
import com.example.cms.domain.menu.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class MenuServiceTest {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MenuService menuService;

    @Test
    @DisplayName("최상위 메뉴는 단건으로 저장 할 수 있다.")
    void saveOne() {
        //given
        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .listOrder(1)
                .build();
        //when
        Long id = menuService.save(menu1);
        Optional<Menu> menuOptional = menuRepository.findById(id);

        //then
        assertThat(menuOptional).isNotEmpty();

    }

    @Test
    @DisplayName("최상위 메뉴와 그 하위 메뉴는 url이 존재한다면 저장 가능하다.")
    void saveTwo() {
        //given
        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .listOrder(1)
                .build();
        menuService.save(menu1);
        Menu menu2 = Menu.builder()
                .name("1-1번메뉴")
                .listOrder(1)
                .url("/abc/test")
                .build();
        //when
        menu2.setParent(menu1);
        menuService.save(menu2);

        //then
        assertThat(menu2.getParent()).isEqualTo(menu1);

    }

    @Test
    @DisplayName("최상위 메뉴는 조회 가능하다.")
    void findOne() {
        //given
        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .listOrder(1)
                .build();
        //when
        Menu saveMenu = menuRepository.save(menu1);
        Optional<Menu> optionalMenu = menuService.findById(saveMenu.getId());

        //then
        assertThat(optionalMenu).isNotEmpty();

    }

    @Test
    @DisplayName("최상위 메뉴는 단건으로 삭제가 가능하다.")
    void deleteById() {
        //given
        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .listOrder(1)
                .build();
        Menu saveMenu = menuRepository.save(menu1);
        Long id = menu1.getId();

        //when
        menuService.deleteById(id);


        //then
        Optional<Menu> optionalMenu = menuRepository.findById(id);
        assertThat(optionalMenu).isEmpty();
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
        Menu menu2 = Menu.builder()
                .name("1-1번메뉴")
                .listOrder(1)
                .url("/abc/test")
                .build();
        menu2.setParent(menu1);
        menuService.save(menu2);
        Long id1 = menu1.getId();
        Long id2 = menu2.getId();

        //when
        menuService.deleteById(id1);
        Optional<Menu> optionalMenu1 = menuRepository.findById(id1);
        Optional<Menu> optionalMenu2 = menuRepository.findById(id2);

        //then
        assertThat(optionalMenu1).isEmpty();
        assertThat(optionalMenu2).isEmpty();
    }
}
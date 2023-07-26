package com.example.cms.domain.menu.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {


    @Test
    @DisplayName("최상위 메뉴가 아니고 상위메뉴가 존재한다면 url이 반드시 있어야한다.")
    void test(){
        //given
        Menu menu1 = Menu.builder()
                .name("1번메뉴")
                .listOrder(1)
                .build();

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            Menu menu2 = Menu.builder()
                    .name("1번메뉴")
                    .listOrder(2)
                    .build();
            menu2.setParent(menu1);
        });


    }

}
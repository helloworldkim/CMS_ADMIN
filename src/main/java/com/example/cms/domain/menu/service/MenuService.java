package com.example.cms.domain.menu.service;

import com.example.cms.domain.menu.dto.MenuResDTO;
import com.example.cms.domain.menu.entity.Menu;
import com.example.cms.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;


    public List<MenuResDTO> getV1Menus() {
        List<Menu> all = menuRepository.findAll();
        return all.stream().map(MenuResDTO::new).collect(Collectors.toList());
    }

    public List<MenuResDTO> getV2Menus() {
        final List<Menu> all = menuRepository.findAllByParentIsNull();
        return all.stream().map(MenuResDTO::new).collect(Collectors.toList());
    }

    public List<MenuResDTO> getV3Menus() {
        final List<Menu> all = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "listOrder"));
        return all.stream().map(MenuResDTO::new).collect(Collectors.toList());
    }

    public List<MenuResDTO> getV4Menus() {
        final List<Menu> all = menuRepository.findAllByParentIsNull(Sort.by(Sort.Direction.ASC, "listOrder")); // parent가 없는 메뉴들을 조회함.
        return all.stream().map(MenuResDTO::new).collect(Collectors.toList());
    }

    public List<MenuResDTO> getV5Menus() {
        List<Menu> all = menuRepository.findMenuListWithQuerydsl();
        return all.stream().map(MenuResDTO::new).collect(Collectors.toList());
    }



}

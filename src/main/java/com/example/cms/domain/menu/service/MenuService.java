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

    public List<MenuResDTO> getMenuResDTOListByQureydsl() {
        return menuRepository.findMenuListWithQuerydsl();
    }



}

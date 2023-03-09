package com.example.cms.domain.menu.service;

import com.example.cms.domain.menu.dto.MenuResDTO;
import com.example.cms.domain.menu.entity.Menu;
import com.example.cms.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuResDTO> getMenuResDTOListByQureydsl() {
        return menuRepository.findMenuListWithQuerydsl();
    }
    @Transactional
    public Long save(Menu menu) {
        return menuRepository.save(menu).getId();
    }

    public Optional<Menu> findById(Long id) {
        return menuRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }
}

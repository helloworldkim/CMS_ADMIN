package com.example.cms.domain.menu.service;

import com.example.cms.domain.menu.dto.MenuResDTO;
import com.example.cms.domain.menu.entity.Menu;
import com.example.cms.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

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

    public List<Menu> getTopLevelMenus() {
        return menuRepository.findByParentIdOrderByListOrderAsc(null);
    }

    public Map<Long, List<Menu>> getChildMenusByParentId(Map<Long, List<Menu>> childMenusByParent, Long parentId) {
        List<Menu> childMenus = menuRepository.findByParentIdOrderByListOrderAsc(parentId);
        childMenusByParent.put(parentId, childMenus);
        return childMenusByParent;
    }
}

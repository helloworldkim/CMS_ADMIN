package com.example.cms.domain.menu.repository;

import com.example.cms.domain.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByParentIdOrderByOrderAsc(Long parentId);
}

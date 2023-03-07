package com.example.cms.domain.menu.repository;

import com.example.cms.domain.menu.dto.MenuResDTO;
import com.example.cms.domain.menu.entity.Menu;

import java.util.List;


public interface MenuCustomRepositroy {
    List<MenuResDTO> findMenuListWithQuerydsl();

}

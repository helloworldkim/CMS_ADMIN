package com.example.cms.domain.adminmenugroup.repository;

import com.example.cms.domain.adminmenugroup.entity.AdminGroupMenu;

import java.util.List;


public interface AdminGroupMenuCustomRepositroy {
    List<AdminGroupMenu> findAdminGroupMenuListWithQuerydsl(Long id);

}

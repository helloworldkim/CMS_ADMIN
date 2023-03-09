package com.example.cms.domain.adminmenugroup.repository;

import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.adminmenugroup.entity.AdminGroupMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminGroupMenuRepository extends JpaRepository<AdminGroupMenu, Long>, AdminGroupMenuCustomRepositroy {
    List<AdminGroupMenu> findByAdminGroup(AdminGroup adminGroup);
}

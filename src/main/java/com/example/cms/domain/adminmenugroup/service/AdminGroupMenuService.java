package com.example.cms.domain.adminmenugroup.service;

import com.example.cms.domain.adminmenugroup.entity.AdminGroupMenu;
import com.example.cms.domain.adminmenugroup.repository.AdminGroupMenuRepository;
import com.example.cms.system.util.MessageUtil;
import com.example.cms.web.controller.admingroupmenu.AdminGroupMenuForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminGroupMenuService {

    private final AdminGroupMenuRepository adminGroupMenuRepository;
    private final MessageUtil messageUtil;

    @Transactional
    public Long save(AdminGroupMenu adminGroupMenu) {
        return adminGroupMenuRepository.save(adminGroupMenu).getId();
    }

    public Optional<AdminGroupMenu> findById(Long id) {
        return adminGroupMenuRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long adminGroupId) {
        adminGroupMenuRepository.deleteById(adminGroupId);
    }

    @Transactional
    public void update(AdminGroupMenuForm adminGroupForm) {
        AdminGroupMenu adminGroupMenu = adminGroupMenuRepository.findById(Long.valueOf(adminGroupForm.getAdminGroupId()))
                .orElseThrow(() -> new IllegalArgumentException(messageUtil.getMessage("message.admin.group.common.unknown.target")));
//        adminGroupMenu.updateAdminGroupMenu(adminGroupForm.getName(), adminGroupForm.getDescription());
    }
}

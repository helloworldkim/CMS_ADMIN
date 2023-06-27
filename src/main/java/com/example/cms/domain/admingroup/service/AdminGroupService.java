package com.example.cms.domain.admingroup.service;

import com.example.cms.domain.admin.repository.AdminRepository;
import com.example.cms.domain.admingroup.dto.AdminGroupDTO;
import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.admingroup.repository.AdminGroupRepository;
import com.example.cms.system.util.MessageUtil;
import com.example.cms.web.controller.group.AdminGroupForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminGroupService {
    
    private final AdminGroupRepository adminGroupRepository;
    private final AdminRepository adminRepository;
    private final MessageUtil messageUtil;
    
    @Transactional
    public Long save(AdminGroup adminGroup) {
        return adminGroupRepository.save(adminGroup).getId();
    }

    public Optional<AdminGroup> findById(Long id) {
        return adminGroupRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long adminGroupId) {
        AdminGroup adminGroup = adminGroupRepository.findById(adminGroupId).orElseThrow(() -> new IllegalArgumentException(messageUtil.getMessage("message.admin.group.common.unknown.target")));
        if (adminRepository.findByAdminGroup(adminGroup).isPresent()) {
            throw new AdminGroupInUseException(messageUtil.getMessage("message.admin.group.in-use"));
        }
        adminGroupRepository.deleteById(adminGroupId);
    }

    public List<AdminGroup> findAll() {
        return adminGroupRepository.findAll();
    }

    public Page<AdminGroupDTO> findAdminGroupList(Pageable pageable) {
        return adminGroupRepository.findAdminGroupList(pageable);
    }

    @Transactional
    public void update(AdminGroupForm adminGroupForm) {
        AdminGroup adminGroup = adminGroupRepository.findById(Long.valueOf(adminGroupForm.getAdminGroupId()))
                .orElseThrow(() -> new IllegalArgumentException(messageUtil.getMessage("message.admin.group.common.unknown.target")));
        adminGroup.updateAdminGroup(adminGroupForm.getName(), adminGroupForm.getDescription());
    }
}

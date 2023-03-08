package com.example.cms.domain.admingroup.service;

import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.admingroup.repository.AdminGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminGroupService {
    
    private final AdminGroupRepository adminGroupRepository;
    
    @Transactional
    public Long save(AdminGroup adminGroup) {
        AdminGroup savedAdminGroup = adminGroupRepository.save(adminGroup);
        return savedAdminGroup.getId();
    }
}

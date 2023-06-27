package com.example.cms.domain.admingroup.service;

import com.example.cms.domain.admingroup.dto.AdminGroupDTO;
import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.admingroup.repository.AdminGroupRepository;
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
    
    @Transactional
    public Long save(AdminGroup adminGroup) {
        return adminGroupRepository.save(adminGroup).getId();
    }

    public Optional<AdminGroup> findById(Long id) {
        return adminGroupRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long adminGroupId) {
        adminGroupRepository.deleteById(adminGroupId);
    }

    public List<AdminGroup> findAll() {
        return adminGroupRepository.findAll();
    }

    public Page<AdminGroupDTO> findAdminGroupList(Pageable pageable) {
        return adminGroupRepository.findAdminGroupList(pageable);
    }
}

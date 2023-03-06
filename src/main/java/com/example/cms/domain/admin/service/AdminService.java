package com.example.cms.domain.admin.service;

import com.example.cms.domain.admin.entity.Admin;
import com.example.cms.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    @Transactional
    public Long save(Admin admin) {
        return adminRepository.save(admin).getId();
    }
    public Admin findById(Long id) {
        return adminRepository.findById(id).orElseThrow();
    }
    public Admin findByAdminId(String adminId) {
        return adminRepository.findByAdminId(adminId).orElseThrow();
    }

    public Page<Admin> findAllWithPage(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }
    @Transactional
    public void delete(Admin admin) {
        adminRepository.delete(admin);
    }
}

package com.example.cms.domain.admin.service;

import com.example.cms.domain.admin.entity.Admin;
import com.example.cms.domain.admin.repository.AdminRepository;
import com.example.cms.domain.adminmenugroup.repository.AdminGroupMenuRepository;
import com.example.cms.system.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Long save(Admin admin) {
        admin.encodePassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin).getId();
    }
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }
    public Optional<Admin> findByAdminId(String adminId) {
        return adminRepository.findByAdminId(adminId);
    }

    public Page<Admin> findAllWithPage(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }
    @Transactional
    public void delete(Admin admin) {
        admin.adminDelete();
    }

}

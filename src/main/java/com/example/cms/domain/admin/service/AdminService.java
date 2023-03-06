package com.example.cms.domain.admin.service;

import com.example.cms.domain.admin.entity.Admin;
import com.example.cms.domain.admin.repository.AdminRepository;
import com.example.cms.domain.authadmin.dto.AuthAdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialNotFoundException;
import javax.security.auth.login.LoginException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
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

    public AuthAdminDTO login(String adminId, String pwd) throws LoginException {

        var admin = adminRepository.findByAdminId(adminId).orElseThrow(() -> new AccountNotFoundException("일치하는 계정 정보가 없습니다."));

        if (!passwordEncoder.matches(pwd, admin.getPassword())) {
            throw new CredentialNotFoundException("비밀번호가 일치하지 않습니다.");
        }

        return new AuthAdminDTO();
    }
}

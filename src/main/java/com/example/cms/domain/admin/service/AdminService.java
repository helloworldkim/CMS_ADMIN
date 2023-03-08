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
import java.util.Optional;

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

    /**
     * 로그인 처리
     * @param adminId 아이디
     * @param password 비밀번호
     * @return AuthAdminDTO
     * @throws CredentialNotFoundException, AccountNotFoundException
     */
    public AuthAdminDTO loginProcess(String adminId, String password) throws CredentialNotFoundException, AccountNotFoundException {

        Admin admin = adminRepository.findByAdminId(adminId).orElseThrow(() -> new AccountNotFoundException("일치하는 계정 정보가 없습니다."));

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new CredentialNotFoundException("비밀번호가 일치하지 않습니다.");
        }

        return admin.toAuthAdminDTO();
    }
}

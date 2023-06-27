package com.example.cms.domain.admin.service;

import com.example.cms.domain.admin.dto.AdminDTO;
import com.example.cms.domain.admin.entity.Admin;
import com.example.cms.domain.admin.repository.AdminRepository;
import com.example.cms.domain.admingroup.entity.AdminGroup;
import com.example.cms.domain.admingroup.repository.AdminGroupRepository;
import com.example.cms.system.enums.AdminRole;
import com.example.cms.system.util.MessageUtil;
import com.example.cms.web.controller.admin.AdminForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.cms.system.constant.RegexConst.PWD_REGEX;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminGroupRepository adminGroupRepository;
    private final MessageUtil messageUtil;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Long save(Admin admin) {
        return adminRepository.save(admin).getId();
    }
    @Transactional
    public void update(AdminForm adminForm) {
        Admin admin = adminRepository.findByAdminId(adminForm.getAdminId()).orElseThrow(() -> new NoSuchElementException(messageUtil.getMessage("message.admin.common.unknown.target")));
        AdminGroup adminGroup = adminGroupRepository.findById(Long.valueOf(adminForm.getAdminGroupId())).orElseThrow(() -> new NoSuchElementException(messageUtil.getMessage("message.admin.common.unknown.target")));
        //비밀번호 존재하면 암호화 처리
        if (!adminForm.getPassword().isBlank()) {
            adminForm.setPassword(passwordEncoder.encode(adminForm.getPassword().trim()));
        }
        //비밀번호 있으면 같이수정, 없으면 수정안됨
        admin.update(adminForm.getPassword()
                , AdminRole.findAdminRole(adminForm.getAdminRole())
                , adminGroup
                , adminForm.getAdminName()
                , adminForm.getEmail());
    }
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }
    public Optional<Admin> findByAdminId(String adminId) {
        return adminRepository.findByAdminId(adminId);
    }

    public Page<AdminDTO> findAllWithPage(Pageable pageable) {
        return adminRepository.findAdminList(pageable);
    }
    @Transactional
    public void deleteByAdminId(String adminId) {
        Admin admin = adminRepository.findByAdminId(adminId).orElseThrow(() -> new NoSuchElementException(messageUtil.getMessage("message.admin.common.unknown.target")));
        admin.adminDelete();
    }

    public boolean validatePassword(String password) {
        // 비밀번호 값 확인 (필수 값)
        if (password.isBlank()) {
            throw new AdminPasswordException(messageUtil.getMessage("common.valid.required"));
        }
        // 비밀번호 규칙 학인
        if (!password.matches(PWD_REGEX)) {
            throw new AdminPasswordException(messageUtil.getMessage("common.valid.pwd.format"));
        }
        return true;
    }
}

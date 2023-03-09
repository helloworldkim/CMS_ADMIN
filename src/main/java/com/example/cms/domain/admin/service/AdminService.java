package com.example.cms.domain.admin.service;

import com.example.cms.domain.admin.entity.Admin;
import com.example.cms.domain.admin.repository.AdminRepository;
import com.example.cms.domain.adminmenugroup.entity.AdminGroupMenu;
import com.example.cms.domain.adminmenugroup.repository.AdminGroupMenuRepository;
import com.example.cms.domain.authadmin.dto.AuthAdminDTO;
import com.example.cms.system.enums.AdminMainAccessType;
import com.example.cms.system.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final MessageUtil messageUtil;
    private final AdminRepository adminRepository;
    private final AdminGroupMenuRepository adminGroupMenuRepository;
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

    /**
     * 로그인 처리
     * @param adminId 아이디
     * @param password 비밀번호
     * @return AuthAdminDTO
     * @throws CredentialNotFoundException, AccountNotFoundException
     */
    public AuthAdminDTO loginProcess(String adminId, String password) throws CredentialNotFoundException, AccountNotFoundException {

        Admin admin = adminRepository.findByAdminId(adminId).orElseThrow(() -> new AccountNotFoundException("아이디를 확인해주세요."));

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new CredentialNotFoundException("비밀번호가 일치하지 않습니다.");
        }

        // ==============================
        // 메뉴 구성
        // ==============================\
        return createAdminMenuInfo(admin);
    }

    private AuthAdminDTO createAdminMenuInfo(Admin admin) {

        log.debug("==> 메뉴 조회 및 세팅 시작");

        Long adminGroupId = admin.getAdminGroup().getId();
        // 접속 가능한 트리메뉴 목록을 가져 온다
        List<AdminGroupMenu> adminGroupAccessMenuList = adminGroupMenuRepository.findAdminGroupMenuListWithQuerydsl(adminGroupId);

        log.trace("==> accessMenuList={}", adminGroupAccessMenuList);

        // 접속 가능한 메뉴가 없을 경우
        if (adminGroupAccessMenuList.isEmpty()) {
            throw new IllegalStateException("접속가능한 메뉴가 없습니다.");
        }

        AuthAdminDTO authAdminDTO = admin.toAuthAdminDTO();

        for (AdminGroupMenu adminGroupMenu : adminGroupAccessMenuList) {

            // Home URL 셋팅
            if (StringUtils.isNotBlank(authAdminDTO.getHomeUrl())) {
                setHomeUrl(authAdminDTO, adminGroupMenu);
            }


        }

        return authAdminDTO;
    }

    private void setHomeUrl(AuthAdminDTO authAdminDTO, AdminGroupMenu adminGroupMenu) {
            if(authAdminDTO.getAdminMainAccessType() == AdminMainAccessType.MAIN) {
                authAdminDTO.setHomeUrl("/");
            } else {
                if (!adminGroupMenu.getMenu().getChildren().isEmpty()) {
                    String firstSubMenuUrl = adminGroupMenu.getMenu().getChildren().get(0).getUrl();
                    authAdminDTO.setHomeUrl(firstSubMenuUrl);
                }
            }
    }

}

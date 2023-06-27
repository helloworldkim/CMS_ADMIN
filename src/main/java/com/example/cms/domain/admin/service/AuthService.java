package com.example.cms.domain.admin.service;

import com.example.cms.domain.admin.entity.Admin;
import com.example.cms.domain.admin.repository.AdminRepository;
import com.example.cms.domain.adminmenugroup.dto.AdminGroupMenuDTO;
import com.example.cms.domain.adminmenugroup.entity.AdminGroupMenu;
import com.example.cms.domain.adminmenugroup.repository.AdminGroupMenuRepository;
import com.example.cms.domain.admin.dto.AuthAdminDTO;
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

import javax.security.auth.login.CredentialNotFoundException;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MessageUtil messageUtil;
    private final AdminRepository adminRepository;
    private final AdminGroupMenuRepository adminGroupMenuRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 처리
     * @param adminId 아이디
     * @param password 비밀번호
     * @return AuthAdminDTO
     * @throws CredentialNotFoundException, AccountNotFoundException
     */
    public AuthAdminDTO loginProcess(String adminId, String password) {

        Admin admin = adminRepository.findByAdminIdActive(adminId)
                .orElseThrow(() -> new AdminLoginException(messageUtil.getMessage("message.auth.login.id")));

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new AdminLoginException(messageUtil.getMessage("message.auth.login.password"));
        }

        // ==============================
        // 메뉴 구성
        // ==============================\
        return createAdminMenuInfo(admin);
    }

    /**
     * 메인메뉴, 메뉴별정보, 권한, 서브메뉴에 대한 정보들을 셋팅한다.
     * @param admin
     * @return authAdminDTO
     */
    private AuthAdminDTO createAdminMenuInfo(Admin admin) {

        log.debug("==> 메뉴 조회 및 세팅 시작");

        Long adminGroupId = admin.getAdminGroup().getId();
        // 어드민 그룹 별 접속 가능한 전체 메뉴 목록을 가져 온다
        List<AdminGroupMenu> adminGroupAccessMenuList = adminGroupMenuRepository.findAdminGroupMenuListWithQuerydsl(adminGroupId);

        log.trace("==> accessMenuList={}", adminGroupAccessMenuList);

        // 접속 가능한 메뉴가 없을 경우
        if (adminGroupAccessMenuList.isEmpty()) {
            throw new IllegalStateException("접속가능한 메뉴가 없습니다.");
        }

        //기본 유저 정보
        AuthAdminDTO authAdminDTO = admin.toAuthAdminDTO();

        //mainMenuList
        List<AdminGroupMenuDTO> mainMenuList = getMainMenuList(adminGroupAccessMenuList);
        //menuMap
        Map<Long, AdminGroupMenuDTO> menuMap = createMenuMap(adminGroupAccessMenuList);
        //authMap
        Map<String, AdminGroupMenuDTO> authMap = createAuthMap(adminGroupAccessMenuList);

        //서브메뉴
        Map<Long, List<AdminGroupMenuDTO>> subMenuMap = new HashMap<>();
        for (AdminGroupMenu adminGroupMenu : adminGroupAccessMenuList) {

            // Home URL 셋팅
            if (StringUtils.isNotBlank(authAdminDTO.getHomeUrl())) {
                setHomeUrl(authAdminDTO, adminGroupMenu);
            }
            Long menuId = adminGroupMenu.getMenu().getId();

            //하위 메뉴가 존재한다면 subMenuList에 값을 채워 넣는다
            boolean empty = adminGroupMenu.getMenu().getChildren().isEmpty();
            if (!empty) {
                //서브메뉴 리스트 생성
                List<AdminGroupMenuDTO> subMenuList = new ArrayList<>();

                adminGroupMenu.getMenu().getChildren().forEach(menu -> {
                    Long subMenuId = menu.getId();
                    AdminGroupMenuDTO adminGroupMenuDTO = menuMap.get(subMenuId);
                    subMenuList.add(adminGroupMenuDTO);
                });

                subMenuMap.put(menuId, subMenuList);
            }


        }
        authAdminDTO.setMainMenuList(mainMenuList);
        authAdminDTO.setSubMenuMap(subMenuMap);
        authAdminDTO.setAuthMap(authMap);
        return authAdminDTO;
    }

    /**
     * 최상위 메뉴만 return한다
     * @param adminGroupAccessMenuList
     * @return
     */
    private List<AdminGroupMenuDTO> getMainMenuList(List<AdminGroupMenu> adminGroupAccessMenuList) {
        return adminGroupAccessMenuList.stream()
                .filter(adminGroupMenu -> {
                    return adminGroupMenu.getMenu().getParent() == null;
                })
                .map(AdminGroupMenu::toAdminGroupMenuDTO)
                .toList();
    }

    /**
     * 각 메뉴별 상세정보를 담은 Map을 return한다.
     * @param adminGroupAccessMenuList
     * @return menuMap
     */
    private Map<Long, AdminGroupMenuDTO> createMenuMap(List<AdminGroupMenu> adminGroupAccessMenuList) {
        Map<Long, AdminGroupMenuDTO> menuMap = new HashMap<>();
        adminGroupAccessMenuList.forEach(adminGroupMenu -> {
            Long menuId = adminGroupMenu.getMenu().getId();
            menuMap.put(menuId, adminGroupMenu.toAdminGroupMenuDTO());
        });
        return menuMap;
    }


    /**
     * 요청 URL별 권한을 담은 Map을 return 한다.
     * @param adminGroupAccessMenuList
     * @returnauthMap
     */
    private Map<String, AdminGroupMenuDTO> createAuthMap(List<AdminGroupMenu> adminGroupAccessMenuList) {
        Map<String, AdminGroupMenuDTO> authMap = new HashMap<>();
        adminGroupAccessMenuList.forEach(adminGroupMenu -> {
            String url = adminGroupMenu.getMenu().getUrl();
            authMap.put(url, adminGroupMenu.toAdminGroupMenuDTO());
        });
        return authMap;
    }

    /**
     * HomeUrl 지정
     * @param authAdminDTO
     * @param adminGroupMenu
     */
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

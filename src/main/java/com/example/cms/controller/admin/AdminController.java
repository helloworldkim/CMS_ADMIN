package com.example.cms.controller.admin;

import com.example.cms.domain.admin.service.AdminService;
import com.example.cms.domain.authadmin.dto.AuthAdminDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.login.LoginException;

import static com.example.cms.system.constant.GlobalConst.SESSION_LOGIN_INFO;
import static com.example.cms.system.util.HttpServletUtil.*;
import static com.example.cms.system.util.HttpServletUtil.removeSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    /**
     * 로그인 Form
     * @return
     */
    @GetMapping("/login")
    public String loginForm() {
        // 1. 로그인 완료 상태 -> home url 로 이동
        if (isExistingSession(SESSION_LOGIN_INFO)) {
            log.debug("==> logged in");
            AuthAdminDTO authAdminDTO = (AuthAdminDTO) getSessionAttribute(SESSION_LOGIN_INFO);
            return "redirect:" + authAdminDTO.getHomeUrl();
        }

        // 2. 미 로그인 상태 -> 로그인 폼 이동
        return "admin/loginForm";
    }
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL) throws LoginException {

        log.debug("==> loginForm={}", form);
        if (bindingResult.hasErrors()) {
            log.debug("==> bindingResult = {}", bindingResult);
            return "admin/loginForm";
        }

        AuthAdminDTO login = adminService.login(form.getAdminId(), form.getPwd());
        // ============================================================================================================
        // 로그인 성공
        // ============================================================================================================
        createSession(SESSION_LOGIN_INFO, login);

        return "redirect:" + redirectURL;
    }
    /**
     * 로그아웃 수행
     * @return
     */
    @GetMapping("/logout")
    public String logout() {

        removeSession();
        return "redirect:/login";
    }

}

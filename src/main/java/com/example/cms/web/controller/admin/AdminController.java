package com.example.cms.web.controller.admin;

import com.example.cms.domain.admin.service.AdminService;
import com.example.cms.domain.admin.dto.AuthAdminDTO;
import com.example.cms.system.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialNotFoundException;

import static com.example.cms.system.constant.GlobalConst.SESSION_LOGIN_INFO;
import static com.example.cms.system.util.HttpServletUtil.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    private final MessageUtil messageUtil;

    /**
     * 로그인 Form
     * @return admin/loginForm
     */
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
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
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL) {


        log.debug("==> loginForm={}", loginForm);
        if (bindingResult.hasErrors()) {
            log.debug("==> bindingResult = {}", bindingResult);
            return "admin/loginForm";
        }
        AuthAdminDTO authAdminDTO = null;
        try {
            authAdminDTO = adminService.loginProcess(loginForm.getAdminId(), loginForm.getPassword());
        } catch (AccountException | CredentialNotFoundException e) {
                bindingResult.reject("password", null, messageUtil.getMessage("message.auth.login.info-error"));
                return "admin/loginForm";
        }


        // ============================================================================================================
        // 로그인 성공
        // ============================================================================================================
        createSession(SESSION_LOGIN_INFO, authAdminDTO);

        return "redirect:" + redirectURL;
    }
    /**
     * 로그아웃 수행
     * @return /login 페이지
     */
    @GetMapping("/logout")
    public String logout() {

        removeSession();
        return "redirect:/login";
    }

}

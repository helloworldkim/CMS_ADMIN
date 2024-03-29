package com.example.cms.web.controller.auth;

import com.example.cms.domain.admin.dto.AuthAdminDTO;
import com.example.cms.domain.admin.service.AdminLoginException;
import com.example.cms.domain.admin.service.AuthService;
import com.example.cms.system.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.cms.system.constant.GlobalConst.SESSION_LOGIN_INFO;
import static com.example.cms.system.util.HttpServletUtil.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
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

        try {
            AuthAdminDTO authAdminDTO = authService.loginProcess(loginForm.getAdminId(), loginForm.getPassword());
            // ============================================================================================================
            // 로그인 성공
            // ============================================================================================================
            createSession(SESSION_LOGIN_INFO, authAdminDTO);
        } catch (AdminLoginException e) {
            log.info("AdminLoginException e = {}", e.getMessage());
            bindingResult.reject("password", null, messageUtil.getMessage("message.auth.login.info-error"));
            return "admin/loginForm";
        }

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

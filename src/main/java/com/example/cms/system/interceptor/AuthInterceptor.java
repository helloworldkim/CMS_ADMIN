package com.example.cms.system.interceptor;

import com.example.cms.domain.authadmin.dto.AuthAdminDTO;
import com.example.cms.system.properties.ProjectProperties;
import com.example.cms.system.util.HttpServletUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.cms.system.constant.GlobalConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private final MessageSource messageSource;
    private final ProjectProperties projectProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("# ============================== AuthInterceptor [preHandle] ======================================");
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();

        // 계정 인증 객체
        AuthAdminDTO authAdminDTO;

        try {
            // ========================================================================================================
            // 1. 관리자 인증 + 권한 체크가 필요 없는 URL
            // ========================================================================================================
            if (isAdminPermitAllUrl(requestURI)) {
                log.debug("# ==> Permit All Url 요청 - {}", requestURI);
                return true;
            }

            // 로그인 여부 확인
            authAdminDTO = isLoggedIn(session);
            if (authAdminDTO == null || StringUtils.isBlank(authAdminDTO.getAdminId())) {
                log.debug("# ==> 미인증 계정 요청, 로그인 페이지로 이동 - /login?redirectURL={}", requestURI);
                response.sendRedirect("/login?redirectURL=" + requestURI);

                throw new AuthException("미인증 계정 요청");
            }

            // ========================================================================================================
            // 2. 관리자 인증은 필요하나 권한 체크가 필요 없는 URL -> 로그인 여부는 위에서 확인 완료
            // ========================================================================================================
            if (requestURI.equals("/") || isAdminPermitAuthUrl(requestURI)) {
                log.debug("# ==> Admin Permit Auth Url 요청 - {}", requestURI);
                return true;
            }

            // ========================================================================================================
            // 3. 관리자 인증 및 권한 체크가 필요한 URL
            // ========================================================================================================
            log.debug("# ==> 관리자 인증 및 권한 체크가 필요한 Url 요청 - {}", requestURI);

            String contextPath = request.getContextPath();
            String requestURIPath = StringUtils.substring(requestURI, 0, requestURI.lastIndexOf("/") + 1);
            String referer = request.getHeader("Referer");

//            AdminMenuResVO AdminMenuResDTO;
//            Map<String, AdminMenuResVO> authMap = authAdminDTO.getAuthMap();

//            if (authMap != null) {
//                csAdminMenuResDTO = authMap.get(getAuthPath(requestURI));
//
//                // 권한이 있으면 - 현재 메뉴 정보를 request 에 담는다, front 에서 메뉴 상태 표시에 사용
//                if (csAdminMenuResDTO != null && StringUtils.equals(csAdminMenuResDTO.getReadAuthYn(), "Y")) {
//                    request.setAttribute(CURRENT_MENU, csAdminMenuResDTO);
//                    log.debug("# ==> 인증 성공");
//                    return true;
//                }
//
                // 권한 오류로 인한 리다이렉트 설정
                log.warn("# ==> 권한 오류로 인한 실패");
                setAuthRedirect(referer, contextPath, request, response, authAdminDTO);
                throw new IllegalStateException("권한 없음");
//            }

        } catch (IllegalStateException | AuthException e) {

            log.error("# ==> 인증 실패!", e);
            return false;

        } finally {

            log.info(
                    "# =================================================================================================");

        }


    }

    private void setAuthRedirect(String referer, String contextPath, HttpServletRequest request,
                                 HttpServletResponse response, AuthAdminDTO authAdminDTO) throws IOException {

        // 메세지 생성
        HttpServletUtil.createSession(ALERT_MSG,
                messageSource.getMessage("message.menu.access.denied", null, LocaleContextHolder.getLocale()));

        // 이동 할 URL
        String redirectUrl = "";

        // referer 존재 시 허용 URL 인지 확인
        if (referer != null) {
            String siteUrl = projectProperties.getSystem().getAdminSiteUrl()
                    .replace("http://", "")
                    .replace("https://", "");

            String tmpReferer = referer
                    .replace("http://", "")
                    .replace("https://", "");

            if (tmpReferer.startsWith(siteUrl)) {
                redirectUrl = referer;
            }
        }

        // 이동 할 URL 이 없을 경우 메인으로 이동
        if (StringUtils.isEmpty(redirectUrl)) {
            redirectUrl = contextPath + authAdminDTO.getHomeUrl();
        }

        response.sendRedirect(redirectUrl);
    }

    /**
     * 세션 값으로 로그인 여부 확인
     *
     * @param session 세션
     * @return 로그인 - true, 미로그인 - false
     */
    private AuthAdminDTO isLoggedIn(HttpSession session) {
        if (session != null && session.getAttribute(SESSION_LOGIN_INFO) != null) {
            AuthAdminDTO authDTO = (AuthAdminDTO) session.getAttribute(SESSION_LOGIN_INFO);

            log.info("# ==> [로그인 정보] adminId={} , adminName={} , adminGroupId={}", authDTO.getAdminId(), authDTO.getAdminName(), authDTO.getAdminGroupId());

            // 로그인 id 를 MDC 에 저장
            MDC.put(REQUEST_ACCESS_ID, authDTO.getAdminId());

            return authDTO;
        }
        return null;
    }

    /**
     * 관리자 인증은 필요하나 권한 체크가 필요 없는 주소
     * @param requestURI
     * @return
     */
    private boolean isAdminPermitAuthUrl(String requestURI) {
        return projectProperties.getSystem().getAdminPermitAuthUrl().stream().anyMatch(url -> StringUtils.startsWith(requestURI, url));
    }

    /**
     * 권한체크 불필요한 주소 체크
     * @param requestURI
     * @return
     */
    private boolean isAdminPermitAllUrl(String requestURI) {
        return projectProperties.getSystem().getAdminPermitAllUrl().stream().anyMatch(url -> StringUtils.startsWith(requestURI, url));
    }

}

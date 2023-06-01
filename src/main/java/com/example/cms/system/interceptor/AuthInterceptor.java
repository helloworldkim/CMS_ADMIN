package com.example.cms.system.interceptor;

import com.example.cms.domain.admin.dto.AuthAdminDTO;
import com.example.cms.domain.adminmenugroup.dto.AdminGroupMenuDTO;
import com.example.cms.system.properties.ProjectProperties;
import com.example.cms.system.util.HttpServletUtil;
import com.example.cms.system.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.cms.system.constant.GlobalConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private final MessageUtil messageUtil;
    private final ProjectProperties projectProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("# ============================== AuthInterceptor [preHandle] ======================================");
        HttpSession session = request.getSession();
        int depthCount = urlDepthCheck(request.getRequestURI(), "/");
        String requestURI = getRequestURIByDepth(request.getRequestURI(), depthCount);

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
            String referer = request.getHeader("Referer");

            AdminGroupMenuDTO adminGroupMenuDTO;
            Map<String, AdminGroupMenuDTO> authMap = authAdminDTO.getAuthMap();

            if (authMap != null) {
                //요청 주소의 접근 권한 확인
                adminGroupMenuDTO = authMap.get(requestURI);
                if (adminGroupMenuDTO == null) {
                    log.info("# ==> 없는 주소요청 requestURI : {} ", requestURI);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "대상메뉴없음");
                    return false;
                }
                boolean menuAccess = adminGroupMenuDTO.menuAccess();
                if (menuAccess) {
                    // 권한이 있으면 - 현재 메뉴 정보를 request 에 담는다, front 에서 메뉴 상태 표시에 사용
                    request.setAttribute(CURRENT_MENU, adminGroupMenuDTO);
                    log.debug("# ==> 인증 성공");
                    return true;
                } else {
                    log.warn("# ==> 대상 메뉴 접근 권한 없음");
                    setAuthRedirect(referer, contextPath, request, response, authAdminDTO);
                    throw new IllegalStateException("대상 메뉴 접근 권한 없음");
                }


            }

        } catch (IllegalStateException | AuthException e) {

            log.error("# ==> 인증 실패!", e);
            return false;

        } finally {

            log.info(
                    "# =================================================================================================");

        }

        return false;
    }

    private String getRequestURIByDepth(String requestURI, int depthCount) {
        if (depthCount < 3) {
            return requestURI;
        }
        return StringUtils.substring(requestURI, 0, requestURI.lastIndexOf("/"));
    }

    /**
     * 특정 문자(/)가 몇번 포함되어있는지 확인 후 체크
     * ex) /login, /system/menu, /system/menu/1 과 같은 url이 있다고할때
     * /login --> 1
     * /system/menu --> 2
     * /system/menu/1 --> 3 을 반환
     * @param requestUri
     * @param targetChar
     * @return
     */
    private int urlDepthCheck(String requestUri, String targetChar) {
        Pattern pattern = Pattern.compile(Pattern.quote(targetChar));
        Matcher matcher = pattern.matcher(requestUri);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private void setAuthRedirect(String referer, String contextPath, HttpServletRequest request,
                                 HttpServletResponse response, AuthAdminDTO authAdminDTO) throws IOException {

        // 메세지 생성
        HttpServletUtil.createSession(ALERT_MSG, messageUtil.getMessage("message.menu.access.denied"));

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
     * 권한체크 불필요한 주소 체크
     * @param requestURI
     * @return
     */
    private boolean isAdminPermitAllUrl(String requestURI) {
        return projectProperties.getSystem().getAdminPermitAllUrl().stream().anyMatch(url -> StringUtils.startsWith(requestURI, url));
    }

    /**
     * 관리자 인증은 필요하나 권한 체크가 필요 없는 주소
     * @param requestURI
     * @return
     */
    private boolean isAdminPermitAuthUrl(String requestURI) {
        return projectProperties.getSystem().getAdminPermitAuthUrl().stream().anyMatch(url -> StringUtils.startsWith(requestURI, url));
    }

}

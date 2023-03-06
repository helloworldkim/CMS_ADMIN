package com.example.cms.system.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * HTTP 서블릿 도우미
 */
@Component
public class HttpServletUtil {


    /**
     * 세션 Attribute 생성
     *
     * @param key   세션 Attribute key 값
     * @param value 저장 할 객체
     */
    public static void createSession(String key, Object value) {
        HttpSession session = getRequest().getSession();
        session.setAttribute(key, value);
    }

    /**
     * 세션 삭제
     */
    public static void removeSession() {
        HttpSession session = getRequest().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * 세션 Attribute 조회
     *
     * @param key Attribute Key 값
     * @return 세션 조회 객체
     */
    public static Object getSessionAttribute(String key) {
        return getRequest().getSession().getAttribute(key);
    }

    /**
     * 세션 Attribute 삭제
     *
     * @param key Attribute Key 값
     * @return 세션 조회 객체
     */
    public static void removeSessionAttribute(String key) {
        Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
                .removeAttribute(key, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 세션 존재 여부 확인
     *
     * @return 세션이 살아있으면 true, null 이면 false
     */
    public static boolean isExistingSession(String key) {
        if (getRequest().getSession() == null) {
            return false;
        }
        if (getSessionAttribute(key) == null) {
            return false;
        }
        return true;
    }

    /**
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

}

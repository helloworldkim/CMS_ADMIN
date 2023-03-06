package com.example.cms.system.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * 시스템 관련 유틸
 */
@Component
@Slf4j
public class SystemUtil {

    private static String serverIp = "";


    /**
     * 클라이언트 IP 조회
     *
     * @return
     */
    public static String getClientIP() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        try {
            String[] ipHeaderCandidates = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                    "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
                    "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

            for (String header : ipHeaderCandidates) {
                String ip = request.getHeader(header);
                if (StringUtils.isNotEmpty(ip) && !StringUtils.equalsIgnoreCase(ip, "unknown")) {
                    return ip;
                }
            }

        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
        }

        return request.getRemoteAddr();
    }

    /**
     * 서버 IP 조회
     * TODO: 데이터 캐시 해서 사용하는 방식으로 변경
     *
     * @return
     */
    public static String getServerIP() {
        if (StringUtils.isBlank(serverIp)) {
            try {
                serverIp = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return serverIp;
    }

    /**
     * 클라이언트 OS 조회
     *
     * @return
     */
    public static String getClientOS() {
        String userAgent = getUserAgent();

        String os = "";
        try {
            if (StringUtils.indexOf(userAgent, "windows nt 10.0") > -1) {
                os = "Windows10";
            } else if (StringUtils.indexOf(userAgent, "windows nt 6.2") > -1
                       || StringUtils.indexOf(userAgent, "windows nt 6.3") > -1) {
                os = "Windows8";
            } else if (StringUtils.indexOf(userAgent, "windows nt 6.1") > -1) {
                os = "Windows7";
            } else if (StringUtils.indexOf(userAgent, "windows nt 6.0") > -1) {
                os = "WindowsVista";
            } else if (StringUtils.indexOf(userAgent, "windows nt 5.1") > -1) {
                os = "WindowsXP";
            } else if (StringUtils.indexOf(userAgent, "windows nt 5.0") > -1) {
                os = "Windows2000";
            } else if (StringUtils.indexOf(userAgent, "windows nt 4.0") > -1) {
                os = "WindowsNT";
            } else if (StringUtils.indexOf(userAgent, "windows 98") > -1) {
                os = "Windows98";
            } else if (StringUtils.indexOf(userAgent, "windows 95") > -1) {
                os = "Windows95";
            } else if (StringUtils.indexOf(userAgent, "iphone") > -1) {
                os = "iPhone";
            } else if (StringUtils.indexOf(userAgent, "ipad") > -1) {
                os = "iPad";
            } else if (StringUtils.indexOf(userAgent, "android") > -1) {
                os = "Android";
            } else if (StringUtils.indexOf(userAgent, "mac") > -1) {
                os = "MAC";
            } else if (StringUtils.indexOf(userAgent, "linux") > -1) {
                os = "Linux";
            } else {
                os = userAgent;
            }
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
        }

        return os;
    }

    /**
     * 클라이언트 브라우저 조회
     *
     * @return
     */
    public static String getClientBrowser() {
        String userAgent = getUserAgent();

        String browser = "Unknown Browser";
        try {
            if (StringUtils.indexOf(userAgent, "edge/") > -1) {
                browser = "Edge";
            } else if (StringUtils.indexOf(userAgent, "trident/7.0") > -1) {
                browser = "IE11";
            } else if (StringUtils.indexOf(userAgent, "msie 10") > -1) {
                browser = "IE10";
            } else if (StringUtils.indexOf(userAgent, "msie 9") > -1) {
                browser = "IE9";
            } else if (StringUtils.indexOf(userAgent, "msie 8") > -1) {
                browser = "IE8";
            } else if (StringUtils.indexOf(userAgent, "msie 7") > -1) {
                if (StringUtils.indexOf(userAgent, "trident/4.0") > -1) {
                    browser = "IE8";
                } else if (StringUtils.indexOf(userAgent, "trident/5.0") > -1) {
                    browser = "IE9";
                } else if (StringUtils.indexOf(userAgent, "trident/6.0") > -1) {
                    browser = "IE10";
                } else if (StringUtils.indexOf(userAgent, "trident/7.0") > -1) {
                    browser = "IE11";
                }
            } else if (StringUtils.indexOf(userAgent, "firefox/") > -1) {
                browser = "Firefox";
            } else if (StringUtils.indexOf(userAgent, "chrome/") > -1 && StringUtils.indexOf(userAgent, "safari/") > -1
                       && StringUtils.indexOf(userAgent, "opr/") > -1) {
                browser = "Opera";
            } else if (StringUtils.indexOf(userAgent, "chrome/") > -1
                       && StringUtils.indexOf(userAgent, "safari/") > -1) {
                browser = "Google Chrome";
            } else if (StringUtils.indexOf(userAgent, "safari/") > -1) {
                browser = "Safari";
            } else if (StringUtils.indexOf(userAgent, "thunderbird") > -1) {
                browser = "Thunderbird";
            } else {
                browser = userAgent;
            }
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
        }

        return browser;
    }

    /**
     * 클라이언트 에이전트 조회
     *
     * @return
     */
    public static String getUserAgent() {
        String userAgent = "";
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            userAgent = StringUtils.defaultIfBlank(request.getHeader("user-agent"), "").toLowerCase();
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
        }

        return userAgent;
    }
}
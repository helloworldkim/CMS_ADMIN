package com.example.cms.system.interceptor;

import com.example.cms.system.util.SystemUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

import static com.example.cms.system.constant.GlobalConst.REQUEST_TRACE_ID;

/**
 * 정보 로깅 인터셉터
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InfoInterceptor implements HandlerInterceptor {
    private static final String              REQUEST_START_TIME = "REQUEST_START_TIME";
    private static final long                WARN_PROCESS_TIME  = 2_000;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // Request 시작 시간 저장
        MDC.put(REQUEST_START_TIME, String.valueOf(System.currentTimeMillis()));

        UUID uuid = UUID.randomUUID();
        MDC.put(REQUEST_TRACE_ID, String.valueOf(uuid));

        log.info("# ============================== InfoInterceptor [preHandle] ======================================");

        logDefaultParameters(request);
        logRequestParameters(request);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // TODO document why this method is empty
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        log.info("# ============================== InfoInterceptor [afterCompletion] ================================");

        try {
            if (ex != null) {
                log.error("==> ERROR: afterCompletion Error!", ex);
                response.sendRedirect("error");
            }

            long requestEndTime = System.currentTimeMillis();
            long requestProcessTime = requestEndTime - Long.parseLong(MDC.get(REQUEST_START_TIME));
            log.info("# Request Process Time: {}ms", requestProcessTime);

            if (requestProcessTime >= WARN_PROCESS_TIME) {
                log.warn("# 요청 처리 시간 {}ms 이상", WARN_PROCESS_TIME);
            }

        } catch (Exception e) {
            log.error("==> ERROR: afterCompletion Error! Exception : {}", e.getMessage());
            throw new Exception(e);
        } finally {
            log.info(
                    "# =================================================================================================");

            // MDC 초기화
            MDC.clear();

            log.info(
                    "# ###########################################################################################################");
        }
    }


    /**
     * 기본 파라미터 정보 출력
     */
    private void logDefaultParameters(HttpServletRequest request) {
        log.info("# ============================== Default Parameters");

        String method = request.getMethod();
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        String requestURIPath = StringUtils.substring(requestURI, 0, requestURI.lastIndexOf("/") + 1);
        String referer = request.getHeader("Referer");
        String contentType = request.getContentType();
        String accept = request.getHeader("Accept");
        String clientOS = SystemUtil.getClientOS();
        String clientBrowser = SystemUtil.getClientBrowser();
        String clientIP = SystemUtil.getClientIP();
        String serverIP = SystemUtil.getServerIP();
        Locale locale = LocaleContextHolder.getLocale();

        log.info("# method: {}", method);
        log.info("# contextPath: {}", contextPath);
        log.info("# requestURI: {}", requestURI);
        log.info("# requestURIPath: {}", requestURIPath);
        log.info("# referer: {}", referer);
        log.info("# contentType: {}", contentType);
        log.info("# accept: {}", accept);
        log.info("# serverIP: {}", serverIP);
        log.info("# clientIP: {} , clientOS: {} , clientBrowser: {}", clientIP, clientOS, clientBrowser);
        log.info("# locale: {}", locale);
    }


    /**
     * Request 파라미터 정보 출력
     * <p>
     * QueryString, FormData
     * </p>
     */
    private void logRequestParameters(HttpServletRequest request) throws IOException {
        log.info("# ============================== Request Parameters");

        StringBuilder requestParameters = new StringBuilder();
        request.getParameterMap().forEach((key, value) -> {
            if (key.equalsIgnoreCase("PASSWORD") || key.equalsIgnoreCase("PWD")) {
                requestParameters.append(key).append("=").append("[****]").append(" ");
            } else {
                requestParameters.append(key).append("=").append(Arrays.toString(value)).append(" ");
            }
        });

        log.info("# ==> requestParameters: {}", requestParameters);
    }



}
package com.example.cms.system.constant;

import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

/**
 * 전역 상수
 */
@Component
@NoArgsConstructor(access = PRIVATE)
public class GlobalConst {

    // ================================================================================================================
    // MDC 상수
    // ================================================================================================================

    /**
     * 로그 추적 ID - uuid 를 MDC 에 저장
     */
    public static final String REQUEST_TRACE_ID = "REQ_TRACE_ID";

    public static Optional<String> getMdcTraceId() {
        return Optional.ofNullable(MDC.get(REQUEST_TRACE_ID));
    }

    /**
     * 로그인 계정 ID 를 MDC 에 저장
     */
    public static final String REQUEST_ACCESS_ID = "REQ_ACCESS_ID";

    public static Optional<String> getMdcAccessId() {
        return Optional.ofNullable(MDC.get(REQUEST_ACCESS_ID));
    }

    public static final String REQ_LOG_ID = "REQ_LOG_ID";

    public static Optional<String> getMdcReqLogId() {
        return Optional.ofNullable(MDC.get(REQ_LOG_ID));
    }

    // ================================================================================================================
    // Session key , Request key
    // ================================================================================================================

    /**
     * 로그인 정보 key
     */
    public static final String SESSION_LOGIN_INFO = "SESSION_LOGIN_INFO";
    /**
     * Alert 메시지 key
     */
    public static final String ALERT_MSG          = "ALERT_MSG";
    /**
     * 모달(팝업) 메시지 key
     */
    public static final String ALERT_MODAL_MSG    = "ALERT_MODAL_MSG";
    /**
     * 현재 메뉴 정보 - AuthInterceptor 에서 생성
     */
    public static final String CURRENT_MENU       = "currentMenu";


}

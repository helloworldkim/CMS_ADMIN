package com.example.cms.system.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class AuthInterceptorTest {

    @Test
    @DisplayName("하나의 뎁스만 가진 메뉴로 접근시 뎁스 카운트는 1로 된다.")
    void oneDepthUrlTest() {
        String requestURI = "/login";
        String targetChar = "/";
        int count = urlDepthCheck(requestURI, targetChar);
        log.info("count = {}", count);
        assertThat(count).isEqualTo(1);
        String requestURIPath = StringUtils.substring(requestURI, 0, requestURI.lastIndexOf("/") + 1);
    }
    @Test
    @DisplayName("두개의 뎁스를 가진 메뉴로 접근시 뎁스 카운트는 2로 된다.")
    void twoDepthUrlTest() {
        String requestURI = "/system/menu";
        String targetChar = "/";
        int count = urlDepthCheck(requestURI, targetChar);
        log.info("count = {}", count);
        assertThat(count).isEqualTo(2);
    }
    @Test
    @DisplayName("두개의 이상의 뎁스를 가진 메뉴로 접근시 뎁스 카운트는 3이 된다.")
    void overTwoDepthUrlTest() {
        String requestURI = "/system/menu/1";
        String targetChar = "/";
        int count = urlDepthCheck(requestURI, targetChar);
        log.info("count = {}", count);
        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("두개이상의 뎁스를 가진 메뉴로 접근시 requestURI는 2뎁스까지 끊어서 읽는다.")
    void requestURITest1() {
        //given
        String requestURI = "/system/menu/1";
        int depthCount = urlDepthCheck(requestURI, "/");
        String requestURIByDepth = getRequestURIByDepth(requestURI, depthCount);
        log.info("requestURIByDepth = {}", requestURIByDepth);

        //then
        assertThat(requestURIByDepth).isEqualTo("/system/menu");
    }

    @Test
    @DisplayName("두개의 뎁스를 가진 메뉴로 접근시 requestURI는 2뎁스까지 끊어서 읽는다.")
    void requestURITest2() {
        //when
        String requestURI = "/system/menu";
        int depthCount = urlDepthCheck(requestURI, "/");
        String requestURIByDepth = getRequestURIByDepth(requestURI, depthCount);
        log.info("requestURIByDepth = {}", requestURIByDepth);

        //then
        assertThat(requestURIByDepth).isEqualTo("/system/menu");
    }

    @Test
    @DisplayName("하나의 뎁스를 가진 메뉴로 접근시 requestURI는 1뎁스까지만 읽는다.")
    void requestURITest3() {
        //given
        String requestURI = "/login";
        int depthCount = urlDepthCheck(requestURI, "/");
        String requestURIByDepth = getRequestURIByDepth(requestURI, depthCount);
        log.info("requestURIByDepth = {}", requestURIByDepth);

        //then
        assertThat(requestURIByDepth).isEqualTo("/login");
    }

    private int urlDepthCheck(String requestURI, String targetChar) {
        Pattern pattern = Pattern.compile(Pattern.quote(targetChar));
        Matcher matcher = pattern.matcher(requestURI);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        log.info("count = {}", count);
        return count;
    }

    private String getRequestURIByDepth(String requestURI, int depthCount) {
        if (depthCount < 3) {
            return requestURI;
        }
        return StringUtils.substring(requestURI, 0, requestURI.lastIndexOf("/"));
    }
}
package com.example.cms.system.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class AuthInterceptorTest {

    @Test
    void oneDepthUrlTest() {
        String requestURI = "/login";
        String targetChar = "/";
        Pattern pattern = Pattern.compile(Pattern.quote(targetChar));
        Matcher matcher = pattern.matcher(requestURI);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        log.info("count = {}", count);
        assertThat(count).isEqualTo(1);
        String requestURIPath = StringUtils.substring(requestURI, 0, requestURI.lastIndexOf("/") + 1);
    }
    @Test
    void twoDepthUrlTest() {
        String requestURI = "/system/menu";
        String targetChar = "/";
        Pattern pattern = Pattern.compile(Pattern.quote(targetChar));
        Matcher matcher = pattern.matcher(requestURI);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        log.info("count = {}", count);
        assertThat(count).isEqualTo(2);
    }
    @Test
    void overTwoDepthUrlTest() {
        String requestURI = "/system/menu/1";
        String targetChar = "/";
        Pattern pattern = Pattern.compile(Pattern.quote(targetChar));
        Matcher matcher = pattern.matcher(requestURI);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        log.info("count = {}", count);
        assertThat(count).isEqualTo(3);
    }

    @Test
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
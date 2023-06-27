package com.example.cms.system.exception;

import com.example.cms.domain.admingroup.service.AdminGroupInUseException;
import com.example.cms.system.BaseJsonVO;
import com.example.cms.system.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.webjars.NotFoundException;

import javax.naming.NoPermissionException;
import javax.security.auth.message.AuthException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionAdvice {

    private final MessageUtil messageUtil;

    /**
     * AdminGroup Error 처리
     * @param e
     * @return
     */
    @ExceptionHandler(AdminGroupInUseException.class)
    @ResponseBody
    public BaseJsonVO adminGroupInUseExceptionHandler(AdminGroupInUseException e) {
        log.error(e.getMessage());
        return BaseJsonVO.builder()
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .error(e.getMessage())
                .build();
    }

    /**
     * 기본 Error처리.
     * @param e
     * @return
     * @throws IOException
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (e instanceof IllegalArgumentException
                || e instanceof IllegalStateException
                || e instanceof TypeMismatchException
                || e instanceof ServletRequestBindingException) {

            httpStatus = HttpStatus.BAD_REQUEST;

        } else if (e instanceof NotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;

        } else if (e instanceof AuthException) {
            httpStatus = HttpStatus.UNAUTHORIZED;

        } else if (e instanceof NoPermissionException) {
            httpStatus = HttpStatus.FORBIDDEN;

        }
        log.error(e.getMessage());

        Map<String, String> resultMap = getDefaultErrorResultMap(httpStatus, messageUtil.getMessage("message.common.error") );
        return new ModelAndView("error", resultMap);
    }

    /**
     * Error 화면
     *
     * @param httpStatus
     * @param exceptionMessage
     * @return
     */
    private Map<String, String> getDefaultErrorResultMap(HttpStatus httpStatus, String exceptionMessage) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", Integer.toString(httpStatus.value()));
        resultMap.put("error", exceptionMessage);
        resultMap.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        log.info("==> sendError - httpStatus={}", httpStatus);
        return resultMap;
    }
}

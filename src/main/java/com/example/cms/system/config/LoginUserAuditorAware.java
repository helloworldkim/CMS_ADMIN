package com.example.cms.system.config;

import com.example.cms.domain.admin.dto.AuthAdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.cms.system.constant.GlobalConst.SESSION_LOGIN_INFO;
import static com.example.cms.system.util.HttpServletUtil.getSessionAttribute;
import static com.example.cms.system.util.HttpServletUtil.isExistingSession;

@RequiredArgsConstructor
@Component
public class LoginUserAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        if (!isExistingSession(SESSION_LOGIN_INFO)) {
            return Optional.empty();
        }

        AuthAdminDTO authDTO = (AuthAdminDTO) getSessionAttribute(SESSION_LOGIN_INFO);
        return Optional.ofNullable(authDTO.getAdminId());
    }
}

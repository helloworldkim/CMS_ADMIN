package com.example.cms.messagesource;

import com.example.cms.system.util.MessageUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class MessageSourceTest {

    @Autowired
    MessageUtil messageUtil;

    @Test
    @DisplayName("adminId message 테스트")
    void messages_test_id() {
        String message = messageUtil.getMessage("message.auth.login.id");
        System.out.println("==> message = " + message);

        assertThat(message).isNotBlank();
    }

    @Test
    @DisplayName("password message 테스트")
    void messages_test_password() {
        String message = messageUtil.getMessage("message.auth.login.password");
        System.out.println("==> message = " + message);

        assertThat(message).isNotBlank();
    }
}

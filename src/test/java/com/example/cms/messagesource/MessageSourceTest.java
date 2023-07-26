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
    @DisplayName("messages를 제대로 읽어오는지 확인한다.(message.auth.login.id)")
    void messages_test_id() {
        String message = messageUtil.getMessage("message.auth.login.id");
        System.out.println("==> message = " + message);

        assertThat(message).isNotBlank();
    }

    @Test
    @DisplayName("messages를 제대로 읽어오는지 확인한다.(message.auth.login.password)")
    void messages_test_password() {
        String message = messageUtil.getMessage("message.auth.login.password");
        System.out.println("==> message = " + message);

        assertThat(message).isNotBlank();
    }
}

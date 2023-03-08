package com.example.cms.messagesource;

import com.example.cms.system.util.MessageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class MessageSourceTest {

    @Autowired
    MessageUtil messageUtil;


    @Test
    void messages_test() {
        String message = messageUtil.getMessage("message.auth.login.id");
        System.out.println("==> message = " + message);

        assertThat(message).isNotBlank();
    }

    @Test
    void messages_common_test() {
        String message = messageUtil.getMessage("message.auth.login.password");
        System.out.println("==> message = " + message);

        assertThat(message).isNotBlank();
    }
}

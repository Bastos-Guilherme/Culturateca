
package com.social.culturateca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.social.culturateca.service.EmailService;


@SpringBootTest
@ActiveProfiles("development")
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void shouldSendEmail() {
        emailService.sendEmail(
            "grsouza06@gmail.com",
            "Teste de envio - Culturateca",
            "Se você recebeu este e-mail, o envio pela Culturateca está funcionando!"
        );
    }
}

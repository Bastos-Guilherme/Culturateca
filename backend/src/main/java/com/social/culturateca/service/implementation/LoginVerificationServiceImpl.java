
package com.social.culturateca.service.implementation;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.culturateca.model.PendingLoginVerification;
import com.social.culturateca.model.VerificationPurpose;
import com.social.culturateca.model.repository.PendingLoginVerificationRepository;
import com.social.culturateca.service.EmailService;
import com.social.culturateca.service.LoginVerificationService;

@Service
public class LoginVerificationServiceImpl
        implements LoginVerificationService {

    private static final int CODE_EXPIRATION_MINUTES = 10;
    private static final int MAX_ATTEMPTS = 5;
    private static final int RESEND_COOLDOWN_SECONDS = 60;

    private final PendingLoginVerificationRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final SecureRandom secureRandom = new SecureRandom();

    public LoginVerificationServiceImpl(
            PendingLoginVerificationRepository repository,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public String requestCode(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        VerificationPurpose purpose = VerificationPurpose.LOGIN_MFA;

        Instant now = Instant.now();

        var existing = repository.findByEmailAndPurpose(
                normalizedEmail, purpose);

        if (existing.isPresent()) {
            PendingLoginVerification previous = existing.get();

            Instant cooldownEnd = previous.getCreatedAt()
                    .plusSeconds(RESEND_COOLDOWN_SECONDS);

            if (now.isBefore(cooldownEnd)) {
                throw new IllegalArgumentException(
                        "Aguarde antes de solicitar outro código.");
            }

            repository.delete(previous);
            repository.flush();
        }

        int codeNumber = secureRandom.nextInt(1_000_000);
        String code = String.format("%06d", codeNumber);
        String challengeId = UUID.randomUUID().toString();

        PendingLoginVerification challenge =
                new PendingLoginVerification();

        challenge.setId(challengeId);
        challenge.setEmail(normalizedEmail);
        challenge.setPurpose(purpose);
        challenge.setCodeHash(passwordEncoder.encode(code));
        challenge.setCreatedAt(now);
        challenge.setExpiresAt(
                now.plusSeconds(CODE_EXPIRATION_MINUTES * 60L));
        challenge.setAttempts(0);

        repository.save(challenge);

        emailService.sendEmail(
                normalizedEmail,
                "Código de verificação de login - Culturateca",
                "Seu código de verificação é: " + code
                        + "\n\nEle expira em 10 minutos."
                        + "\nSe você não tentou entrar na sua conta, "
                        + "ignore este e-mail."
        );

        return challengeId;
    }

    @Override
    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public String verifyCode(String challengeId, String code) {
        if (challengeId == null || challengeId.isBlank()
                || code == null || !code.matches("\\d{6}")) {
            throw new IllegalArgumentException(
                    "Código de verificação inválido.");
        }

        PendingLoginVerification challenge = repository
                .findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Código inválido ou expirado."));

        if (challenge.getPurpose() != VerificationPurpose.LOGIN_MFA) {
            throw new IllegalArgumentException(
                    "Código inválido ou expirado.");
        }

        if (!Instant.now().isBefore(challenge.getExpiresAt())) {
            repository.delete(challenge);
            throw new IllegalArgumentException(
                    "Código inválido ou expirado.");
        }

        if (challenge.getAttempts() >= MAX_ATTEMPTS) {
            repository.delete(challenge);
            throw new IllegalArgumentException(
                    "Limite de tentativas excedido. Faça login novamente.");
        }

        if (!passwordEncoder.matches(code, challenge.getCodeHash())) {
            challenge.setAttempts(challenge.getAttempts() + 1);

            if (challenge.getAttempts() >= MAX_ATTEMPTS) {
                repository.delete(challenge);
            } else {
                repository.save(challenge);
            }

            throw new IllegalArgumentException("Código incorreto.");
        }

        String email = challenge.getEmail();

        repository.delete(challenge);

        return email;
    }
}


package com.social.culturateca.service.implementation;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.culturateca.model.Curator;
import com.social.culturateca.model.PendingLoginVerification;
import com.social.culturateca.model.VerificationPurpose;
import com.social.culturateca.model.repository.CuratorRepository;
import com.social.culturateca.model.repository.PendingLoginVerificationRepository;
import com.social.culturateca.service.EmailService;
import com.social.culturateca.service.PasswordRecoveryService;

@Service
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

    private static final int CODE_EXPIRATION_MINUTES = 10;
    private static final int MAX_ATTEMPTS = 5;
    private static final int RESEND_COOLDOWN_SECONDS = 60;

    private final PendingLoginVerificationRepository verificationRepository;
    private final CuratorRepository curatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final SecureRandom secureRandom = new SecureRandom();

    public PasswordRecoveryServiceImpl(
            PendingLoginVerificationRepository verificationRepository,
            CuratorRepository curatorRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {
        this.verificationRepository = verificationRepository;
        this.curatorRepository = curatorRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public String requestCode(String email) {
        String challengeId = UUID.randomUUID().toString();

        if (email == null || email.isBlank()) {
            return challengeId;
        }

        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);

        Curator curator = curatorRepository.findByEmail(normalizedEmail);

        if (curator == null) {
            return challengeId;
        }

        VerificationPurpose purpose = VerificationPurpose.PASSWORD_RECOVERY;

        Instant now = Instant.now();

        var existing = verificationRepository
                .findByEmailAndPurpose(normalizedEmail, purpose);

        if (existing.isPresent()) {
            PendingLoginVerification previous = existing.get();

            Instant cooldownEnd = previous.getCreatedAt()
                    .plusSeconds(RESEND_COOLDOWN_SECONDS);

            if (now.isBefore(cooldownEnd)) {
                return previous.getId();
            }

            verificationRepository.delete(previous);
            verificationRepository.flush();
        }

        String code = String.format(
                "%06d", secureRandom.nextInt(1_000_000));

        PendingLoginVerification challenge = new PendingLoginVerification();

        challenge.setId(challengeId);
        challenge.setEmail(normalizedEmail);
        challenge.setPurpose(purpose);
        challenge.setCodeHash(passwordEncoder.encode(code));
        challenge.setCreatedAt(now);
        challenge.setExpiresAt(
                now.plusSeconds(CODE_EXPIRATION_MINUTES * 60L));
        challenge.setAttempts(0);

        verificationRepository.saveAndFlush(challenge);

        emailService.sendEmail(
                normalizedEmail,
                "Recuperação de senha - Culturateca",
                "Recebemos uma solicitação para redefinir sua senha."
                        + "\n\nSeu código de recuperação é: " + code
                        + "\n\nEle expira em 10 minutos."
                        + "\nSe você não solicitou a recuperação, "
                        + "ignore este e-mail.");

        return challengeId;
    }

    @Override
    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public void verifyCode(String challengeId, String code) {
        findValidChallenge(challengeId, code);
    }

    @Override
    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public void resetPassword(
            String challengeId,
            String code,
            String newPassword) {

        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException(
                    "A nova senha deve ter pelo menos 8 caracteres.");
        }

        PendingLoginVerification challenge = findValidChallenge(challengeId, code);

        Curator curator = curatorRepository.findByEmail(challenge.getEmail());

        if (curator == null) {
            verificationRepository.delete(challenge);
            throw new IllegalArgumentException(
                    "Não foi possível redefinir a senha.");
        }

        curator.setPassword(passwordEncoder.encode(newPassword));
        curatorRepository.save(curator);

        verificationRepository.delete(challenge);
    }

    private PendingLoginVerification findValidChallenge(
            String challengeId,
            String code) {

        if (challengeId == null || challengeId.isBlank()
                || code == null || !code.matches("\\d{6}")) {
            throw new IllegalArgumentException(
                    "Código inválido ou expirado.");
        }

        PendingLoginVerification challenge = verificationRepository
                .findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Código inválido ou expirado."));

        if (challenge.getPurpose() != VerificationPurpose.PASSWORD_RECOVERY) {
            throw new IllegalArgumentException(
                    "Código inválido ou expirado.");
        }

        if (!Instant.now().isBefore(challenge.getExpiresAt())) {
            verificationRepository.delete(challenge);
            throw new IllegalArgumentException(
                    "Código inválido ou expirado.");
        }

        if (challenge.getAttempts() >= MAX_ATTEMPTS) {
            verificationRepository.delete(challenge);
            throw new IllegalArgumentException(
                    "Limite de tentativas excedido. Solicite outro código.");
        }

        if (!passwordEncoder.matches(code, challenge.getCodeHash())) {
            challenge.setAttempts(challenge.getAttempts() + 1);

            if (challenge.getAttempts() >= MAX_ATTEMPTS) {
                verificationRepository.delete(challenge);
            } else {
                verificationRepository.save(challenge);
            }

            throw new IllegalArgumentException("Código incorreto.");
        }

        return challenge;
    }
}

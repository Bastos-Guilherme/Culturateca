
package com.social.culturateca.service.implementation;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.culturateca.controller.DTO.RegisterRequestDTO;
import com.social.culturateca.controller.DTO.VerifyRegistrationDTO;
import com.social.culturateca.model.Curator;
import com.social.culturateca.model.Location;
import com.social.culturateca.model.PendingCurator;
import com.social.culturateca.model.repository.CuratorRepository;
import com.social.culturateca.model.repository.PendingCuratorRepository;
import com.social.culturateca.service.CuratorService;
import com.social.culturateca.service.EmailService;
import com.social.culturateca.service.LocationService;
import com.social.culturateca.service.RegistrationVerificationService;

@Service
public class RegistrationVerificationServiceImpl implements RegistrationVerificationService {

    private static final int CODE_LENGTH = 6;
    private static final int MAX_ATTEMPTS = 5;

    private static final Duration CODE_VALIDITY = Duration.ofMinutes(10);

    private static final Duration RESEND_INTERVAL = Duration.ofSeconds(60);

    private final PendingCuratorRepository pendingRepository;
    private final CuratorRepository curatorRepository;
    private final LocationService locationService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private final SecureRandom secureRandom = new SecureRandom();

    private final CuratorService curatorService;

    public RegistrationVerificationServiceImpl(
            PendingCuratorRepository pendingRepository,
            CuratorRepository curatorRepository,
            LocationService locationService,
            EmailService emailService,
            PasswordEncoder passwordEncoder,
            CuratorService curatorService) {

        this.pendingRepository = pendingRepository;
        this.curatorRepository = curatorRepository;
        this.locationService = locationService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.curatorService = curatorService;
    }

    @Override
    @Transactional
    public void requestCode(RegisterRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "Os dados do cadastro são obrigatórios.");
        }

        String email = normalizeEmail(request.email());
        String name = request.name() == null
                ? ""
                : request.name().trim();

        if (email.isBlank() || name.isBlank()
                || request.password() == null
                || request.password().isBlank()) {
            throw new IllegalArgumentException(
                    "Nome, e-mail e senha são obrigatórios.");
        }

        if (curatorRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException(
                    "Não foi possível iniciar o cadastro com esse e-mail.");
        }

        Instant now = Instant.now();

        PendingCurator pending = pendingRepository
                .findByEmail(email)
                .orElseGet(PendingCurator::new);

        if (pending.getId() != null
                && pending.getLastSentAt() != null
                && now.isBefore(
                        pending.getLastSentAt().plus(RESEND_INTERVAL))) {
            throw new IllegalArgumentException(
                    "Aguarde 60 segundos antes de solicitar outro código.");
        }

        Location location = null;

        if (request.locationId() != null) {
            location = locationService.findById(request.locationId());

            if (location == null) {
                throw new IllegalArgumentException(
                        "A localização informada não foi encontrada.");
            }
        }

        String code = generateCode();

        pending.setEmail(email);
        pending.setName(name);
        pending.setPassword(passwordEncoder.encode(request.password()));
        pending.setGender(request.gender());
        pending.setPhone(request.phone());
        pending.setBio(request.bio());
        pending.setLocation(location);
        pending.setIsPublic(request.isPublic() == null ? true : request.isPublic());
        pending.setFollowing(List.of());
        pending.setProfilePicture(request.profilePicture());
        pending.setCodeHash(passwordEncoder.encode(code));
        pending.setExpiresAt(now.plus(CODE_VALIDITY));
        pending.setLastSentAt(now);
        pending.setAttempts(0);

        pendingRepository.saveAndFlush(pending);

        emailService.sendEmail(
                email,
                "Código de verificação - Culturateca",
                """
                Olá, %s!

                Seu código de verificação da Culturateca é: %s

                O código expira em 10 minutos.
                Se você não solicitou esse cadastro, ignore este e-mail.
                """.formatted(name, code));
    }

    @Override
    @Transactional(noRollbackFor = RegistrationException.class)
    public void verifyCode(VerifyRegistrationDTO request) {
        if (request == null
                || request.email() == null
                || request.code() == null) {
            throw new RegistrationException(
                    "Informe o e-mail e o código de verificação.");
        }

        String email = normalizeEmail(request.email());
        String code = request.code().trim();

        PendingCurator pending = pendingRepository
                .findByEmail(email)
                .orElseThrow(() -> new RegistrationException(
                        "Cadastro pendente não encontrado. Solicite um novo código."));

        Instant now = Instant.now();

        if (!now.isBefore(pending.getExpiresAt())) {
            pendingRepository.delete(pending);
            throw new RegistrationException(
                    "O código expirou. Solicite um novo código.");
        }

        if (pending.getAttempts() >= MAX_ATTEMPTS) {
            pendingRepository.delete(pending);
            throw new RegistrationException(
                    "Limite de tentativas atingido. Solicite um novo código.");
        }

        if (!passwordEncoder.matches(code, pending.getCodeHash())) {
            pending.setAttempts(pending.getAttempts() + 1);
            pendingRepository.saveAndFlush(pending);

            throw new RegistrationException(
                    "Código inválido. Confira o código e tente novamente.");
        }

        if (curatorRepository.findByEmail(email) != null) {
            pendingRepository.delete(pending);
            throw new RegistrationException(
                    "Não foi possível concluir o cadastro com esse e-mail.");
        }

        Curator curator = new Curator();
        curator.setEmail(pending.getEmail());
        curator.setName(pending.getName());
        curator.setPassword(pending.getPassword());
        curator.setGender(pending.getGender());
        curator.setPhone(pending.getPhone());
        curator.setBio(pending.getBio());
        curator.setLocation(pending.getLocation());
        curator.setIsPublic(true);
        curator.setFollowing(
                pending.getFollowing() == null
                        ? List.of()
                        : pending.getFollowing());
        curator.setProfilePicture(pending.getProfilePicture());

        curatorService.createVerifiedCurator(curator);
        pendingRepository.delete(pending);
    }

    private String generateCode() {
        int number = secureRandom.nextInt(1_000_000);
        return String.format("%0" + CODE_LENGTH + "d", number);
    }

    private String normalizeEmail(String email) {
        return email == null
                ? ""
                : email.trim().toLowerCase(Locale.ROOT);
    }
}

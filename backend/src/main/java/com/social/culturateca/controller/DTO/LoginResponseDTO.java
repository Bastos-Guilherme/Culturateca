
package com.social.culturateca.controller.DTO;

public record LoginResponseDTO(
    boolean mfaRequired,
    String challengeId,
    String message
) {}
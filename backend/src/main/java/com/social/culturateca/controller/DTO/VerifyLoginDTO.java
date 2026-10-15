
package com.social.culturateca.controller.DTO;

public record VerifyLoginDTO(
    String challengeId,
    String code
) {}

package com.social.culturateca.controller.DTO;

import com.social.culturateca.model.Gender;

public record RegisterRequestDTO(
        String name,
        String email,
        String password,
        Gender gender,
        String phone,
        String bio,
        Long locationId,
        Boolean isPublic,
        String profilePicture
) {
}

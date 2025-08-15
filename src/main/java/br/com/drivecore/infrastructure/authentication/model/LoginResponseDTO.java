package br.com.drivecore.infrastructure.authentication.model;

import lombok.Builder;

@Builder
public record LoginResponseDTO(
        String accessToken,
        long expiresIn
) {
}

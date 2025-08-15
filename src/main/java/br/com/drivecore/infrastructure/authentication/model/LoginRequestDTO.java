package br.com.drivecore.infrastructure.authentication.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequestDTO(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}

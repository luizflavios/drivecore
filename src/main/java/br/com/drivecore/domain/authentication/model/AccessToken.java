package br.com.drivecore.domain.authentication.model;

public record AccessToken(
        String accessToken,
        long expiresIn
) {
}

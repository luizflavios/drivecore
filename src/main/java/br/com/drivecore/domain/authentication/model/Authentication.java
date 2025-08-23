package br.com.drivecore.domain.authentication.model;

import org.springframework.security.core.userdetails.User;

public record Authentication(User user, AccessToken token) {
}

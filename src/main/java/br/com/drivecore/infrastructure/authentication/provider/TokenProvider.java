package br.com.drivecore.infrastructure.authentication.provider;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.User;

public interface TokenProvider {

    String generateToken(User user);

    void validateToken(String token);

    Claims getClaims(String token);

}

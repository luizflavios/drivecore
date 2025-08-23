package br.com.drivecore.domain.authentication.provider;

import br.com.drivecore.domain.authentication.model.AccessToken;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.User;

public interface TokenProvider {

    AccessToken generateToken(User user);

    void validateToken(String token);

    Claims getClaims(String token);

}

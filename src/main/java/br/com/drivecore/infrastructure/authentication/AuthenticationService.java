package br.com.drivecore.infrastructure.authentication;

import br.com.drivecore.infrastructure.authentication.provider.AuthenticationProvider;
import br.com.drivecore.infrastructure.authentication.provider.PasswordProvider;
import br.com.drivecore.infrastructure.authentication.provider.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationProvider authenticationProvider;
    private final TokenProvider tokenProvider;
    private final PasswordProvider passwordProvider;

    public Authentication getAuthenticationByUsernameAndPassword(String username, String password) {
        return authenticationProvider.authenticateByUsernameAndPassword(username, password);
    }

    public String generateToken(Authentication authentication) {
        return tokenProvider.generateToken((User) authentication.getPrincipal());
    }

    public long getTokenExpirationTime(String token) {
        var claims = getClaims(token);

        return claims.getExpiration().getTime();
    }

    public Claims getClaims(String token) {
        return tokenProvider.getClaims(token);
    }

    public String generateTemporaryPassword() {
        return passwordProvider.generateRandomPassword();
    }
}

package br.com.drivecore.infrastructure.authentication.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication authenticateByUsernameAndPassword(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }

}

package br.com.drivecore.domain.authentication.provider.impl;

import br.com.drivecore.domain.authentication.provider.AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public Authentication currentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}

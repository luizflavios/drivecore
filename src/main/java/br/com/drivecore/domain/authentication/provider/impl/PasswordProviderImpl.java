package br.com.drivecore.domain.authentication.provider.impl;

import br.com.drivecore.core.configuration.AppConfiguration;
import br.com.drivecore.domain.authentication.provider.PasswordProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordProviderImpl implements PasswordProvider {

    private final PasswordEncoder passwordEncoder;
    private final AppConfiguration appConfiguration;

    @Override
    public String generateTempPassword() {
        String tempPassword = appConfiguration.getTemporaryPassword();

        return encodePassword(tempPassword);
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}

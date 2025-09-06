package br.com.drivecore.domain.authentication.provider.impl;

import br.com.drivecore.domain.authentication.provider.PasswordProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class PasswordProviderImpl implements PasswordProvider {

    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private final PasswordEncoder passwordEncoder;

    @Override
    public String generateTempPassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARSET.charAt(RANDOM.nextInt(CHARSET.length())));
        }
        return sb.toString();
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}

package br.com.drivecore.infrastructure.authentication.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class PasswordProviderImpl implements PasswordProvider {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*";
    private static final int PASSWORD_LENGTH = 12;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final PasswordEncoder passwordEncoder;

    @Override
    public String generateRandomPassword() {
        var sb = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = SECURE_RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        
        return sb.toString();
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}

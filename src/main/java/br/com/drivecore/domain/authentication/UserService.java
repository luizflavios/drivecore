package br.com.drivecore.domain.authentication;

import br.com.drivecore.infrastructure.authentication.provider.PasswordProvider;
import br.com.drivecore.infrastructure.messaging.EmailSenderProvider;
import br.com.drivecore.infrastructure.messaging.model.EmailDTO;
import br.com.drivecore.infrastructure.persistence.authentication.UserRepository;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static br.com.drivecore.domain.authentication.enums.UserStatus.ACTIVE;
import static br.com.drivecore.domain.authentication.enums.UserStatus.CONFIGURATION;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String WELCOME = "Transportadora TL - Seja bem vindo";
    private static final String WELCOME_TEMPLATE = "welcome-email";
    private static final String PASSWORD = "password";

    private final UserRepository userRepository;
    private final EmailSenderProvider emailSenderProvider;
    private final PasswordProvider passwordProvider;

    public void createUser(UserEntity user) {
        checkUserPasswordRequirements(user);

        saveUser(user);

        welcomeEmail(user);
    }

    private void welcomeEmail(UserEntity user) {
        if (isNotBlank(user.getTemporaryPassword())) {
            sendWelcomeEmail(user);
        }
    }

    private void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    public void updatePassword(String username, String password) {
        UserEntity user = getUserEntityByUsername(username);

        if (user.getStatus().equals(CONFIGURATION)) {
            user.setPassword(passwordProvider.encodePassword(password));

            user.setStatus(ACTIVE);
        }

        saveUser(user);
    }

    public UserEntity getUserEntityByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("%s does not exists", username)));
    }

    private void checkUserPasswordRequirements(UserEntity user) {
        if (isBlank(user.getPassword())) {
            generateUserPassword(user);
        } else {
            String password = passwordProvider.encodePassword(user.getPassword());

            user.setPassword(password);
        }

        user.setStatus(ACTIVE);
    }

    private void generateUserPassword(UserEntity user) {
        String tempPassword = passwordProvider.generateTempPassword();

        user.setPassword(passwordProvider.encodePassword(tempPassword));

        user.setTemporaryPassword(tempPassword);
    }

    private void sendWelcomeEmail(UserEntity user) {
        EmailDTO emailDTO = new EmailDTO(
                user.getEmail(),
                WELCOME,
                WELCOME_TEMPLATE,
                Map.of(PASSWORD, user.getTemporaryPassword())
        );

        emailSenderProvider.sendEmail(emailDTO);
    }

    public UserEntity getUserEntityById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(format("%s does not exists", id)));
    }

    public void updateUser(UserEntity user) {
        saveUser(user);
    }

    public void putUserOnConfigurationSituation(UserEntity user) {
        user.setStatus(CONFIGURATION);

        saveUser(user);
    }
}

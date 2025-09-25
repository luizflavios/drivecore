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

    private static final String WELCOME = "Seja bem vindo";
    private static final String WELCOME_TEMPLATE = "welcome-email";
    private static final String FORGET_PASSWORD_TEMPLATE = "forget-password-email";
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

    private UserEntity getUserEntityByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("%s does not exists", username)));
    }

    private void checkUserPasswordRequirements(UserEntity user) {
        if (isBlank(user.getPassword())) {
            generateUserTemporaryPassword(user);
        } else {
            String password = passwordProvider.encodePassword(user.getPassword());

            user.setStatus(ACTIVE);

            user.setPassword(password);
        }
    }

    private void generateUserTemporaryPassword(UserEntity user) {
        String tempPassword = passwordProvider.generateTempPassword();

        user.setPassword(passwordProvider.encodePassword(tempPassword));

        user.setStatus(CONFIGURATION);

        user.setTemporaryPassword(tempPassword);
    }

    public void resetUserPassword(UUID id) {
        UserEntity user = getUserEntityById(id);

        generateUserTemporaryPassword(user);

        saveUser(user);

        sendTemporaryPasswordEmail(user);
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

    private void sendTemporaryPasswordEmail(UserEntity user) {
        EmailDTO emailDTO = new EmailDTO(
                user.getEmail(),
                WELCOME,
                FORGET_PASSWORD_TEMPLATE,
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

    public void forgetPassword(String username) {
        UserEntity user = getUserEntityByUsername(username);

        generateUserTemporaryPassword(user);

        saveUser(user);

        sendTemporaryPasswordEmail(user);
    }
}

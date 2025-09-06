package br.com.drivecore.domain.authentication;

import br.com.drivecore.domain.authentication.model.Authentication;
import br.com.drivecore.domain.authentication.provider.AuthenticationProvider;
import br.com.drivecore.domain.authentication.provider.PasswordProvider;
import br.com.drivecore.domain.authentication.provider.TokenProvider;
import br.com.drivecore.infrastructure.persistence.authentication.RoleRepository;
import br.com.drivecore.infrastructure.persistence.authentication.UserRepository;
import br.com.drivecore.infrastructure.persistence.authentication.entities.RoleEntity;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.drivecore.domain.authentication.enums.UserStatus.ACTIVE;
import static br.com.drivecore.domain.authentication.enums.UserStatus.CONFIGURATION;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private static final int PASSWORD_LENGTH = 8;

    private final AuthenticationProvider authenticationProvider;
    private final PasswordProvider passwordProvider;
    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void createUser(UserEntity user) {
        checkUserPasswordRequirements(user);

        userRepository.save(user);
    }

    public Authentication authenticateByUsernameAndPassword(String username, String password) {
        var authentication = authenticationProvider.authenticateByUsernameAndPassword(username, password);

        var user = (User) authentication.getPrincipal();

        var accessToken = tokenProvider.generateToken(user);

        return new Authentication(user, accessToken);
    }

    private void checkUserPasswordRequirements(UserEntity user) {
        if (isBlank(user.getPassword())) {
            String tempPassword = passwordProvider.generateTempPassword(PASSWORD_LENGTH);

            user.setPassword(passwordProvider.encodePassword(tempPassword));

            user.setStatus(CONFIGURATION);

            user.setTemporaryPassword(tempPassword);
        } else {
            String password = passwordProvider.encodePassword(user.getPassword());

            user.setPassword(password);
        }
    }

    public String getCurrentSub() {
        return authenticationProvider
                .currentAuthentication()
                .getPrincipal().toString();
    }

    public List<RoleEntity> findAllRoles() {
        return roleRepository.findAll();
    }

    public void updatePassword(String username, String password) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        user.setPassword(passwordProvider.encodePassword(password));

        if (user.getStatus().equals(CONFIGURATION)) {
            user.setStatus(ACTIVE);
        }

        userRepository.save(user);
    }
}

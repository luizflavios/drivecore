package br.com.drivecore.domain.authentication;

import br.com.drivecore.domain.authentication.model.Authentication;
import br.com.drivecore.domain.authentication.provider.AuthenticationProvider;
import br.com.drivecore.domain.authentication.provider.PasswordProvider;
import br.com.drivecore.domain.authentication.provider.TokenProvider;
import br.com.drivecore.infrastructure.persistence.authentication.UserRepository;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationProvider authenticationProvider;
    private final PasswordProvider passwordProvider;
    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;

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
            String password = passwordProvider.generateTempPassword();

            user.setPassword(password);
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

}

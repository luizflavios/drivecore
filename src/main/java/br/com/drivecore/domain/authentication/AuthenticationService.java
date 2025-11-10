package br.com.drivecore.domain.authentication;

import br.com.drivecore.domain.authentication.model.Authentication;
import br.com.drivecore.infrastructure.authentication.provider.AuthenticationProvider;
import br.com.drivecore.infrastructure.authentication.provider.TokenProvider;
import br.com.drivecore.infrastructure.persistence.authentication.RoleRepository;
import br.com.drivecore.infrastructure.persistence.authentication.UserRepository;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final AuthenticationProvider authenticationProvider;

    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Authentication authenticateByUsernameAndPassword(String username, String password) {
        var authentication = authenticationProvider.authenticateByUsernameAndPassword(username, password);

        var user = (User) authentication.getPrincipal();

        var accessToken = tokenProvider.generateToken(user);

        return new Authentication(user, accessToken);
    }

    public UserEntity getLoggedUser() {
        return userRepository.findById(UUID.fromString(getCurrentSub())).orElseThrow(EntityNotFoundException::new);
    }

    public String getCurrentSub() {
        return authenticationProvider
                .currentAuthentication()
                .getPrincipal().toString();
    }

}

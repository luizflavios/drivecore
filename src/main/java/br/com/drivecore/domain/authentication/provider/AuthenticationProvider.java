package br.com.drivecore.domain.authentication.provider;

import org.springframework.security.core.Authentication;

public interface AuthenticationProvider {

    Authentication authenticateByUsernameAndPassword(String username, String password);

    Authentication currentAuthentication();

}

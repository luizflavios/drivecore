package br.com.drivecore.application.authentication;

import br.com.drivecore.infrastructure.authentication.AuthenticationService;
import br.com.drivecore.infrastructure.authentication.model.LoginRequestDTO;
import br.com.drivecore.infrastructure.authentication.model.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationApplicationService {

    private final AuthenticationService authenticationService;

    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
        var authentication = authenticationService
                .getAuthenticationByUsernameAndPassword(loginRequestDTO.username(), loginRequestDTO.password());

        var token = authenticationService.generateToken(authentication);

        return new LoginResponseDTO(token, authenticationService.getTokenExpirationTime(token));
    }

}

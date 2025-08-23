package br.com.drivecore.application.authentication;

import br.com.drivecore.controller.authentication.model.AuthenticationRequestDTO;
import br.com.drivecore.controller.authentication.model.AuthenticationResponseDTO;
import br.com.drivecore.controller.authentication.model.CreateUserRequestDTO;
import br.com.drivecore.controller.model.ObjectReferenceDTO;
import br.com.drivecore.domain.authentication.AuthenticationService;
import br.com.drivecore.domain.authentication.mapper.AuthenticationMapper;
import br.com.drivecore.domain.authentication.model.Authentication;
import br.com.drivecore.domain.mapper.BaseMapper;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationApplicationService {

    private final AuthenticationService authenticationService;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) {
        Authentication authentication = authenticationService.authenticateByUsernameAndPassword(
                authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword()
        );

        log.info("User successfully authenticate - {}", authentication.user().getUsername());

        return AuthenticationMapper.INSTANCE.toAuthenticationResponseDTO(authentication);
    }

    public ObjectReferenceDTO createUser(CreateUserRequestDTO createUserRequestDTO) {
        UserEntity user = createDomainUser(createUserRequestDTO);

        log.info("User successfully created - {}", user.getId());

        return BaseMapper.INSTANCE.toIdReferenceDTO(user);
    }

    public UserEntity createDomainUser(CreateUserRequestDTO createUserRequestDTO) {
        UserEntity user = AuthenticationMapper.INSTANCE.toUserEntity(createUserRequestDTO);

        authenticationService.createUser(user);

        return user;
    }

    public UUID getLoggedUserId() {
        return UUID.fromString(authenticationService.getCurrentSub());
    }
}

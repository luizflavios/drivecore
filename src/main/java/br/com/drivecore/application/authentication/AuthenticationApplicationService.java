package br.com.drivecore.application.authentication;

import br.com.drivecore.controller.authentication.model.*;
import br.com.drivecore.controller.model.ObjectReferenceDTO;
import br.com.drivecore.domain.authentication.AuthenticationService;
import br.com.drivecore.domain.authentication.mapper.AuthenticationMapper;
import br.com.drivecore.domain.authentication.model.Authentication;
import br.com.drivecore.domain.communication.CommunicationService;
import br.com.drivecore.domain.communication.model.MailDTO;
import br.com.drivecore.domain.mapper.BaseMapper;
import br.com.drivecore.infrastructure.persistence.authentication.entities.RoleEntity;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.drivecore.application.authentication.constants.AuthenticationApplicationServiceConstants.WELCOME_SUBJECT;
import static br.com.drivecore.application.authentication.constants.AuthenticationApplicationServiceConstants.buildWelcomeTextWithTemporaryCredentials;
import static io.micrometer.common.util.StringUtils.isNotBlank;
import static java.util.Collections.emptyList;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationApplicationService {

    private final AuthenticationService authenticationService;
    private final CommunicationService communicationService;

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

        if (isNotBlank(user.getTemporaryPassword())) {
            sendMailWithTemporaryCredentials(user.getEmail(), user.getUsername(), user.getTemporaryPassword());
        }

        return user;
    }

    public UUID getLoggedUserId() {
        return UUID.fromString(authenticationService.getCurrentSub());
    }


    private void sendMailWithTemporaryCredentials(String email, String username, String credentials) {
        MailDTO mailDTO = new MailDTO(
                email,
                WELCOME_SUBJECT,
                buildWelcomeTextWithTemporaryCredentials(username, credentials),
                emptyList()
        );

        communicationService.sendEmail(mailDTO);

        log.info("Welcome email with temporary credentials sent");
    }

    public List<RoleResponseDTO> listRoles() {
        List<RoleEntity> roles = authenticationService.findAllRoles();

        return roles.stream()
                .map(AuthenticationMapper.INSTANCE::toRoleResponseDTO)
                .toList();
    }

    public void updatePassword(@Valid UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        authenticationService.updatePassword(updatePasswordRequestDTO.getUsername(), updatePasswordRequestDTO.getPassword());

        log.info("Password successfully updated - username {}", updatePasswordRequestDTO.getUsername());
    }
}

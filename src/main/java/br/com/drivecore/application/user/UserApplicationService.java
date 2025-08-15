package br.com.drivecore.application.user;

import br.com.drivecore.core.generics.GenericMapper;
import br.com.drivecore.core.generics.IdReferenceGenericDTO;
import br.com.drivecore.domain.user.UserService;
import br.com.drivecore.domain.user.model.CreateUserRequestDTO;
import br.com.drivecore.infrastructure.authentication.AuthenticationService;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserApplicationService {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public IdReferenceGenericDTO createUser(CreateUserRequestDTO createUserRequestDTO) {
        var userEntity = createUserEntity(createUserRequestDTO);

        return GenericMapper.INSTANCE.toIdReferenceGenericDTO(userEntity);
    }

    public UserEntity createUserEntity(CreateUserRequestDTO createUserRequestDTO) {
        checkIfNecessaryCreateTemporaryPassword(createUserRequestDTO);

        var userEntity = userService.create(createUserRequestDTO);

        log.info("User {} created to username - {}", userEntity.getId(), createUserRequestDTO.getUsername());

        return userEntity;
    }

    public UserEntity getLoggedUser() {
        var sub = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        return getUserEntity(sub);
    }

    public UserEntity findById(UUID id) {
        return getUserEntity(id);
    }

    private UserEntity getUserEntity(UUID id) {
        return userService.findById(id);
    }

    private void checkIfNecessaryCreateTemporaryPassword(CreateUserRequestDTO createUserRequestDTO) {
        if (isBlank(createUserRequestDTO.getPassword())) {
            createUserRequestDTO.setPassword(authenticationService.generateTemporaryPassword());

            log.info("Temporary password created to username - {}", createUserRequestDTO.getUsername());

            createUserRequestDTO.setTempPassword(true);
        } else {
            createUserRequestDTO.setTempPassword(false);
        }
    }

}

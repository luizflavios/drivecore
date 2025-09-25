package br.com.drivecore.application.authentication;

import br.com.drivecore.controller.authentication.model.*;
import br.com.drivecore.domain.authentication.AuthenticationService;
import br.com.drivecore.domain.authentication.RoleService;
import br.com.drivecore.domain.authentication.UserService;
import br.com.drivecore.domain.authentication.mapper.AuthenticationMapper;
import br.com.drivecore.domain.authentication.mapper.RoleMapper;
import br.com.drivecore.domain.authentication.mapper.UserMapper;
import br.com.drivecore.domain.authentication.model.Authentication;
import br.com.drivecore.infrastructure.persistence.authentication.entities.RoleEntity;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationApplicationService {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final RoleService roleService;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) {
        Authentication authentication = authenticationService.authenticateByUsernameAndPassword(
                authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword()
        );

        return AuthenticationMapper.INSTANCE.toAuthenticationResponseDTO(authentication);
    }

    public UserResponseDTO createUser(@Valid CreateUserRequestDTO createUserRequestDTO) {
        UserEntity user = createUserEntity(createUserRequestDTO);

        log.info("User {} has successfully created", user.getId());

        return UserMapper.INSTANCE.toUserResponseDTO(user);
    }

    public UserEntity createUserEntity(CreateUserRequestDTO createUserRequestDTO) {
        Set<RoleEntity> basicRoles = new HashSet<>(roleService.findBasicRoles());

        UserEntity user = UserMapper.INSTANCE.toUserEntity(createUserRequestDTO, basicRoles);

        userService.createUser(user);

        return user;
    }

    public void updateUser(UUID id, @Valid UpdateUserRequestDTO updateUserRequestDTO) {
        UserEntity user = userService.getUserEntityById(id);

        UserMapper.INSTANCE.updateUserEntity(user, updateUserRequestDTO);

        userService.updateUser(user);

        log.info("User {} has successfully updated", id);
    }

    public UserResponseDTO getUserDetail(UUID id) {
        UserEntity user = userService.getUserEntityById(id);

        return UserMapper.INSTANCE.toUserResponseDTO(user);
    }

    public void resetUserPassword(UUID id) {
        userService.resetUserPassword(id);

        log.info("User {} has password successfully reset", id);
    }

    public void updateUserPassword(@Valid UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        userService.updatePassword(updatePasswordRequestDTO.getUsername(), updatePasswordRequestDTO.getPassword());

        log.info("Username {} has password successfully updated", updatePasswordRequestDTO.getUsername());
    }

    public List<RoleResponseDTO> listRoles() {
        log.info("Fetching all roles");

        return roleService
                .findAllRoles()
                .stream()
                .map(RoleMapper.INSTANCE::toRoleResponseDTO)
                .toList();
    }

    public void forgetPassword(ForgetPasswordRequestDTO forgetPasswordRequestDTO) {
        userService.forgetPassword(forgetPasswordRequestDTO.getUsername());
    }
}

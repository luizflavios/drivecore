package br.com.drivecore.domain.authentication.mapper;

import br.com.drivecore.controller.authentication.model.AuthenticationResponseDTO;
import br.com.drivecore.controller.authentication.model.CreateUserRequestDTO;
import br.com.drivecore.controller.authentication.model.RoleResponseDTO;
import br.com.drivecore.domain.authentication.enums.UserStatus;
import br.com.drivecore.domain.authentication.model.Authentication;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;

@Mapper(imports = {UserStatus.class})
public interface AuthenticationMapper {

    AuthenticationMapper INSTANCE = Mappers.getMapper(AuthenticationMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", expression = "java(UserStatus.ACTIVE)")
    UserEntity toUserEntity(CreateUserRequestDTO createUserRequestDTO);

    @Mapping(target = "sub", source = "user.username")
    @Mapping(target = "accessToken", source = "token.accessToken")
    @Mapping(target = "expiresIn", source = "token.expiresIn")
    @Mapping(target = "authorities", source = "user.authorities")
    @Mapping(target = "credentialsNonExpired", source = "user.credentialsNonExpired")
    @Mapping(target = "accountNonExpired", source = "user.accountNonExpired")
    @Mapping(target = "accountNonLocked", source = "user.accountNonLocked")
    @Mapping(target = "enabled", source = "user.enabled")
    AuthenticationResponseDTO toAuthenticationResponseDTO(Authentication authentication);

    @Mapping(target = "name", expression = "java(grantedAuthority.getAuthority())")
    RoleResponseDTO toRoleResponseDTO(GrantedAuthority grantedAuthority);

}

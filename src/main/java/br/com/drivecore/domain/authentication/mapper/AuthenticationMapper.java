package br.com.drivecore.domain.authentication.mapper;

import br.com.drivecore.controller.authentication.model.AuthenticationResponseDTO;
import br.com.drivecore.domain.authentication.model.Authentication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthenticationMapper {

    AuthenticationMapper INSTANCE = Mappers.getMapper(AuthenticationMapper.class);

    @Mapping(target = "sub", source = "user.username")
    @Mapping(target = "accessToken", source = "token.accessToken")
    @Mapping(target = "expiresIn", source = "token.expiresIn")
    @Mapping(target = "authorities", source = "user.authorities")
    AuthenticationResponseDTO toAuthenticationResponseDTO(Authentication authentication);

}

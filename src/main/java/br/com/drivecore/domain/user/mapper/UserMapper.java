package br.com.drivecore.domain.user.mapper;

import br.com.drivecore.domain.user.enums.UserStatus;
import br.com.drivecore.domain.user.model.CreateUserRequestDTO;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {UserStatus.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "status", expression = "java(createUserRequestDTO.isTempPassword() ? UserStatus.CONFIGURATION : UserStatus.ACTIVE)")
    UserEntity toEntity(CreateUserRequestDTO createUserRequestDTO);

}

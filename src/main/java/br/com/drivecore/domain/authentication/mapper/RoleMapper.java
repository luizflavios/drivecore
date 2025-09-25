package br.com.drivecore.domain.authentication.mapper;

import br.com.drivecore.controller.authentication.model.RoleResponseDTO;
import br.com.drivecore.infrastructure.persistence.authentication.entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleResponseDTO toRoleResponseDTO(RoleEntity roleEntity);

}

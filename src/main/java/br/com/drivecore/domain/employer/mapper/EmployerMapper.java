package br.com.drivecore.domain.employer.mapper;

import br.com.drivecore.controller.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.controller.employer.model.EmployerResponseDTO;
import br.com.drivecore.domain.authentication.enums.UserStatus;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {UserStatus.class})
public interface EmployerMapper {

    EmployerMapper INSTANCE = Mappers.getMapper(EmployerMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "user", target = "user")
    EmployerEntity toEntity(CreateEmployerRequestDTO createEmployerRequestDTO, UserEntity user);

    EmployerResponseDTO toEmployerResponseDTO(EmployerEntity entity);

}

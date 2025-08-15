package br.com.drivecore.domain.employer.mapper;

import br.com.drivecore.domain.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.domain.employer.model.EmployerRequestDTO;
import br.com.drivecore.domain.employer.model.EmployerResponseDTO;
import br.com.drivecore.domain.user.model.CreateUserRequestDTO;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployerMapper {

    EmployerMapper INSTANCE = Mappers.getMapper(EmployerMapper.class);

    @Mapping(target = "tempPassword", ignore = true)
    @Mapping(target = "username", source = "document")
    @Mapping(target = "password", expression = "java(employerRequestDTO.getUser() != null ? employerRequestDTO.getUser().getPassword() : null)")
    @Mapping(target = "roles", expression = "java(employerRequestDTO.getUser() != null ? employerRequestDTO.getUser().getRoles() : null)")
    CreateUserRequestDTO toCreateUserRequestDTO(CreateEmployerRequestDTO employerRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "user", source = "user")
    EmployerEntity toEntity(CreateEmployerRequestDTO employerRequestDTO, UserEntity user);

    EmployerResponseDTO toEmployerResponseDTO(EmployerEntity employer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "user", ignore = true)
    void copyProperties(EmployerRequestDTO employerRequestDTO, @MappingTarget EmployerEntity employer);

}



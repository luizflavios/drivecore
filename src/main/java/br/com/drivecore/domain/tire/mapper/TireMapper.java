package br.com.drivecore.domain.tire.mapper;

import br.com.drivecore.controller.tire.model.CreateTirePositionDTO;
import br.com.drivecore.controller.tire.model.CreateTireRequestDTO;
import br.com.drivecore.controller.tire.model.TirePositionResponseDTO;
import br.com.drivecore.controller.tire.model.TireResponseDTO;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TireEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TirePositionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TireMapper {

    TireMapper INSTANCE = Mappers.getMapper(TireMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "reconditioning", expression = "java(createTireRequestDTO.getReconditioning() != null ? createTireRequestDTO.getReconditioning() : 0)")
    TireEntity toTireEntity(CreateTireRequestDTO createTireRequestDTO, EmployerEntity createdBy);

    TireResponseDTO toTireResponseDTO(TireEntity tireEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", source = "createdBy")
    TirePositionEntity toTirePositionEntity(CreateTirePositionDTO createTireRequestDTO, EmployerEntity createdBy);

    TirePositionResponseDTO toTirePositionResponseDTO(TirePositionEntity tirePositionEntity);

}

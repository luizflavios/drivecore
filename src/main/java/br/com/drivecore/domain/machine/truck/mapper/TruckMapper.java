package br.com.drivecore.domain.machine.truck.mapper;

import br.com.drivecore.controller.machine.truck.model.CreateTruckRequestDTO;
import br.com.drivecore.controller.machine.truck.model.TruckResponseDTO;
import br.com.drivecore.domain.authentication.enums.UserStatus;
import br.com.drivecore.domain.machine.enums.MachineType;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.machine.truck.entities.TruckEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {UserStatus.class, MachineType.class})
public interface TruckMapper {

    TruckMapper INSTANCE = Mappers.getMapper(TruckMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "type", expression = "java(MachineType.TRUCK)")
    TruckEntity toEntity(CreateTruckRequestDTO createTruckRequestDTO, EmployerEntity createdBy);

    TruckResponseDTO toTruckResponseDTO(TruckEntity entity);

}

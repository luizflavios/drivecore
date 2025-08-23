package br.com.drivecore.domain.machine.mapper;

import br.com.drivecore.controller.machine.model.CreateMachineRequestDTO;
import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import br.com.drivecore.domain.authentication.enums.UserStatus;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {UserStatus.class})
public interface MachineMapper {

    MachineMapper INSTANCE = Mappers.getMapper(MachineMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", source = "createdBy")
    MachineEntity toEntity(CreateMachineRequestDTO createMachineRequestDTO, EmployerEntity createdBy);

    MachineResponseDTO toMachineResponseDTO(MachineEntity entity);

}

package br.com.drivecore.domain.machine.truck.mapper;

import br.com.drivecore.controller.machine.truck.model.CreateTruckRequestDTO;
import br.com.drivecore.controller.machine.truck.model.TruckResponseDTO;
import br.com.drivecore.domain.authentication.enums.UserStatus;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import br.com.drivecore.infrastructure.persistence.machine.truck.entities.TruckEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {UserStatus.class})
public interface TruckMapper {

    TruckMapper INSTANCE = Mappers.getMapper(TruckMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "machine", source = "machine")
    TruckEntity toEntity(CreateTruckRequestDTO createTruckRequestDTO, MachineEntity machine);

    TruckResponseDTO toTruckResponseDTO(TruckEntity entity);

}

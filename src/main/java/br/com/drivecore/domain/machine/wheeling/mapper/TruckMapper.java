package br.com.drivecore.domain.machine.wheeling.mapper;

import br.com.drivecore.controller.machine.model.CreateTruckRequestDTO;
import br.com.drivecore.controller.machine.model.TruckResponseDTO;
import br.com.drivecore.controller.machine.model.UpdateTruckRequestDTO;
import br.com.drivecore.domain.machine.enums.MachineType;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {MachineType.class})
public interface TruckMapper {

    TruckMapper INSTANCE = Mappers.getMapper(TruckMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "machineType", expression = "java(MachineType.TRUCK)")
    TruckEntity toEntity(CreateTruckRequestDTO createTruckRequestDTO);

    TruckResponseDTO toResponseDTO(TruckEntity truck);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget TruckEntity truckEntity, UpdateTruckRequestDTO requestDTO);

}

package br.com.drivecore.domain.tire.mapper;

import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import br.com.drivecore.controller.tire.model.*;
import br.com.drivecore.domain.tire.enums.TireCondition;
import br.com.drivecore.domain.tire.model.TireReport;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TireEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TirePositionEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TireRetreadingEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(imports = {TireCondition.class})
public interface TireMapper {

    TireMapper INSTANCE = Mappers.getMapper(TireMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "tireCondition", expression = "java(createTireRequestDTO.getTireCondition() != null ? createTireRequestDTO.getTireCondition() : TireCondition.NEW)")
    TireEntity toEntity(CreateTireRequestDTO createTireRequestDTO);

    @Mapping(target = "totalMileage", ignore = true)
    TireResponseDTO toResponseDTO(TireEntity tireEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget TireEntity tireEntity, UpdateTireRequestDTO updateTireRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "inUse", constant = "true")
    TirePositionEntity toTirePositionEntity(TireEntity tire, MachineEntity machine, int axle, int side);

    TirePositionResponseDTO toTirePositionEntityResponseDTO(TirePositionEntity tirePositionEntity);

    default TirePositionByMachineResponseDTO toTirePositionByMachineResponseDTO(MachineEntity machineEntity, List<TirePositionEntity> tirePositionEntityList) {
        return TirePositionByMachineResponseDTO.builder()
                .machine(new MachineResponseDTO(machineEntity.getId(), machineEntity.getMachineType()))
                .tiresPositions(tirePositionEntityList.stream().map(this::toTirePositionEntityResponseDTO).toList())
                .build();
    }

    @Mapping(target = "mileage", source = "mileage")
    @Mapping(target = "tire", source = "tire")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    TireRetreadingEntity toTireRetreadingEntity(TireEntity tire);

    TireReport toTireReport(TireResponseDTO tireResponseDTO);

}
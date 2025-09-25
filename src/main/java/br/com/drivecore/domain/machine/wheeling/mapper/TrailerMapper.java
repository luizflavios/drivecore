package br.com.drivecore.domain.machine.wheeling.mapper;

import br.com.drivecore.controller.machine.model.CreateTrailerRequestDTO;
import br.com.drivecore.controller.machine.model.TrailerResponseDTO;
import br.com.drivecore.controller.machine.model.UpdateTrailerRequestDTO;
import br.com.drivecore.domain.machine.enums.MachineType;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {MachineType.class})
public interface TrailerMapper {

    TrailerMapper INSTANCE = Mappers.getMapper(TrailerMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "machineType", expression = "java(MachineType.TRAILER)")
    TrailerEntity toEntity(CreateTrailerRequestDTO createTrailerRequestDTO);

    TrailerResponseDTO toResponseDTO(TrailerEntity trailer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget TrailerEntity trailerEntity, UpdateTrailerRequestDTO requestDTO);

}

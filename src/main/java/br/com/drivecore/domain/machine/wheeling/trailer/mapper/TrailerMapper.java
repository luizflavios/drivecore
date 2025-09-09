package br.com.drivecore.domain.machine.wheeling.trailer.mapper;

import br.com.drivecore.controller.machine.wheeling.trailer.model.CreateTrailerRequestDTO;
import br.com.drivecore.controller.machine.wheeling.trailer.model.TrailerResponseDTO;
import br.com.drivecore.domain.machine.enums.MachineType;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {MachineType.class})
public interface TrailerMapper {

    TrailerMapper INSTANCE = Mappers.getMapper(TrailerMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "machineType", expression = "java(MachineType.TRAILER)")
    TrailerEntity toEntity(CreateTrailerRequestDTO createTrailerRequestDTO, EmployerEntity createdBy);

    TrailerResponseDTO toResponseDTO(TrailerEntity trailerEntity);

}

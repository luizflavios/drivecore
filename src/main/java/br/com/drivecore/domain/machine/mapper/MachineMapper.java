package br.com.drivecore.domain.machine.mapper;

import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import br.com.drivecore.domain.machine.enums.MachineType;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {MachineType.class})
public interface MachineMapper {

    MachineMapper INSTANCE = Mappers.getMapper(MachineMapper.class);

    MachineResponseDTO toResponseDTO(MachineEntity machine);

}

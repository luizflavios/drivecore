package br.com.drivecore.domain.machine.wheeling.mapper;

import br.com.drivecore.controller.machine.model.TruckTrailerCombinationResponseDTO;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckTrailerCombinationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TruckTrailerCombinationMapper {

    TruckTrailerCombinationMapper INSTANCE = Mappers.getMapper(TruckTrailerCombinationMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    TruckTrailerCombinationEntity toEntity(TruckEntity truck, TrailerEntity trailer);

    TruckTrailerCombinationResponseDTO toResponseDTO(TruckTrailerCombinationEntity truckTrailerCombinationEntity);

}

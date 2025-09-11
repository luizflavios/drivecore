package br.com.drivecore.domain.mapper;

import br.com.drivecore.controller.machine.wheeling.truck.model.model.ObjectReferenceDTO;
import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BaseMapper {

    BaseMapper INSTANCE = Mappers.getMapper(BaseMapper.class);

    ObjectReferenceDTO toIdReferenceDTO(BaseEntity baseEntity);

}

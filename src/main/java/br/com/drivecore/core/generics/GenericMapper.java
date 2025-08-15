package br.com.drivecore.core.generics;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenericMapper {

    GenericMapper INSTANCE = Mappers.getMapper(GenericMapper.class);

    IdReferenceGenericDTO toIdReferenceGenericDTO(GenericEntity genericEntity);

}

package br.com.drivecore.core.generics.domain.mapper;

import br.com.drivecore.core.generics.domain.model.IdReferenceGenericDTO;
import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenericMapper {

    GenericMapper INSTANCE = Mappers.getMapper(GenericMapper.class);

    IdReferenceGenericDTO toIdReferenceGenericDTO(BaseEntity genericEntity);

}

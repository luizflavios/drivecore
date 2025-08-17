package br.com.drivecore.core.generics.domain.mapper;

import br.com.drivecore.core.generics.domain.model.BaseRequestDTO;
import br.com.drivecore.core.generics.domain.model.BaseResponseDTO;
import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import org.mapstruct.MappingTarget;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseMapper<T extends BaseRequestDTO, D extends BaseResponseDTO, E extends BaseEntity> {

    E toEntity(T requestDTO);

    D toDTO(E entity);

    void copyProperties(T requestDTO, @MappingTarget E entity);

}
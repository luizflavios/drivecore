package br.com.drivecore.core.generics.domain;

import br.com.drivecore.core.generics.domain.model.BaseRequestDTO;
import br.com.drivecore.core.generics.domain.model.BaseResponseDTO;
import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import br.com.drivecore.infrastructure.persistence.specification.GenericSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public sealed interface BaseDomainService<T extends BaseRequestDTO, R extends BaseResponseDTO, E extends BaseEntity> permits BaseDomainServiceImpl {

    E create(T requestDTO);

    E update(UUID id, T requestDTO);

    E findById(UUID id);

    Page<E> findPageable(Pageable pageable);

    Page<E> findPageableAndFiltered(Pageable pageable, GenericSpecification<E> genericSpecification);

}

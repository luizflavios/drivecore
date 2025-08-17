package br.com.drivecore.core.generics.domain;

import br.com.drivecore.core.generics.domain.exception.GenericEntityNotFoundException;
import br.com.drivecore.core.generics.domain.mapper.BaseMapper;
import br.com.drivecore.core.generics.domain.model.BaseRequestDTO;
import br.com.drivecore.core.generics.domain.model.BaseResponseDTO;
import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import br.com.drivecore.core.generics.infrastructure.persistence.BaseRepository;
import br.com.drivecore.infrastructure.persistence.specification.GenericSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RequiredArgsConstructor
public abstract non-sealed class BaseDomainServiceImpl<T extends BaseRequestDTO, R extends BaseResponseDTO, E extends BaseEntity> implements BaseDomainService<T, R, E> {

    protected final BaseRepository<E> repository;
    protected final BaseMapper<T, R, E> genericMapper;

    @Override
    public E create(T requestDTO) {
        E entity = genericMapper.toEntity(requestDTO);

        return repository.save(entity);
    }

    @Override
    public E findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new GenericEntityNotFoundException(id));
    }

    @Override
    public E update(UUID id, T requestDTO) {
        E entity = findById(id);

        genericMapper.copyProperties(requestDTO, entity);

        return repository.save(entity);
    }

    @Override
    public Page<E> findPageable(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<E> findPageableAndFiltered(Pageable pageable, GenericSpecification<E> genericSpecification) {
        return repository.findAll(genericSpecification, pageable);
    }

}

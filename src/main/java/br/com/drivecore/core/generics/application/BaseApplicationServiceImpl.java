package br.com.drivecore.core.generics.application;

import br.com.drivecore.core.generics.domain.BaseDomainService;
import br.com.drivecore.core.generics.domain.mapper.BaseMapper;
import br.com.drivecore.core.generics.domain.model.BaseRequestDTO;
import br.com.drivecore.core.generics.domain.model.BaseResponseDTO;
import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import br.com.drivecore.infrastructure.persistence.specification.GenericSpecification;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Slf4j
@RequiredArgsConstructor
public abstract non-sealed class BaseApplicationServiceImpl<T extends BaseRequestDTO, R extends BaseResponseDTO, E extends BaseEntity>
        implements BaseApplicationService<T, R, E> {

    protected final BaseDomainService<T, R, E> baseDomainService;
    protected final BaseMapper<T, R, E> genericMapper;

    private static PageRequest getPageable(Integer page, Integer size) {
        return PageRequest.of(page, size);
    }

    @Override
    public R create(T requestDTO) {
        E entity = baseDomainService.create(requestDTO);

        log.info("{} successfully created - {}", entity.getClass().getSimpleName(), entity.getId());

        return genericMapper.toDTO(entity);
    }

    @Override
    public R update(UUID id, T requestDTO) {
        E entity = baseDomainService.update(id, requestDTO);

        log.info("{} successfully updated - {}", entity.getClass().getSimpleName(), entity.getId());

        return genericMapper.toDTO(entity);
    }

    @Override
    public R findById(UUID id) {
        E entity = baseDomainService.findById(id);

        return genericMapper.toDTO(entity);
    }

    @Override
    public Page<R> findPageable(Integer page, Integer size) {
        var pageable = getPageable(page, size);

        Page<E> entityPage = baseDomainService.findPageable(pageable);

        return buildPageResponseDTO(entityPage, pageable);
    }

    @Override
    public Page<R> findPageableAndFiltered(Integer page, Integer size, List<FilterCriteria> criteria) {
        var pageable = getPageable(page, size);

        var specification = new GenericSpecification<E>(criteria);

        Page<E> entityPage = baseDomainService.findPageableAndFiltered(pageable, specification);

        return buildPageResponseDTO(entityPage, pageable);
    }

    private PageImpl<R> buildPageResponseDTO(Page<E> entityPage, PageRequest pageable) {
        if (entityPage.hasContent()) {
            var response = entityPage
                    .getContent()
                    .stream()
                    .map(genericMapper::toDTO)
                    .toList();

            return new PageImpl<R>(response, pageable, entityPage.getTotalElements());
        }

        return new PageImpl<R>(emptyList(), pageable, entityPage.getTotalElements());
    }
}

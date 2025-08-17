package br.com.drivecore.core.generics.application;

import br.com.drivecore.core.generics.domain.model.BaseRequestDTO;
import br.com.drivecore.core.generics.domain.model.BaseResponseDTO;
import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public sealed interface BaseApplicationService<T extends BaseRequestDTO, R extends BaseResponseDTO, E extends BaseEntity> permits BaseApplicationServiceImpl {

    R create(@NotNull T requestDTO);

    R update(@NotNull UUID id, @NotNull T requestDTO);

    R findById(@NotNull UUID id);

    Page<R> findPageable(@NotNull Integer page, @NotNull Integer size);

    Page<R> findPageableAndFiltered(@NotNull Integer page, @NotNull Integer size, @NotNull List<FilterCriteria> criteria);

}

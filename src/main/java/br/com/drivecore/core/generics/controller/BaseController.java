package br.com.drivecore.core.generics.controller;

import br.com.drivecore.core.generics.domain.model.BaseRequestDTO;
import br.com.drivecore.core.generics.domain.model.BaseResponseDTO;
import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface BaseController<T extends BaseRequestDTO, R extends BaseResponseDTO, E extends BaseEntity> {

    ResponseEntity<R> create(@RequestBody @Valid T requestDTO);

    ResponseEntity<R> update(@PathVariable UUID id, @RequestBody @Valid T requestDTO);

    ResponseEntity<R> findById(@PathVariable UUID id);

    ResponseEntity<Page<R>> findPageable(@RequestParam Integer page, @RequestParam Integer size);

    ResponseEntity<Page<R>> findPageableAndFiltered(@RequestParam Integer page, @RequestParam Integer size, @RequestBody @Valid List<FilterCriteria> criteria);

}

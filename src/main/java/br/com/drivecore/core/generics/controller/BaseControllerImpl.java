package br.com.drivecore.core.generics.controller;

import br.com.drivecore.core.generics.application.BaseApplicationService;
import br.com.drivecore.core.generics.domain.model.BaseRequestDTO;
import br.com.drivecore.core.generics.domain.model.BaseResponseDTO;
import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
public abstract class BaseControllerImpl<T extends BaseRequestDTO, R extends BaseResponseDTO, E extends BaseEntity> implements BaseController<T, R, E> {

    protected final BaseApplicationService<T, R, E> baseApplicationService;

    @Override
    @PostMapping
    public ResponseEntity<R> create(T requestDTO) {
        R response = baseApplicationService.create(requestDTO);

        return new ResponseEntity<>(response, CREATED);
    }

    @Override
    @PutMapping
    public ResponseEntity<R> update(UUID id, T requestDTO) {
        R response = baseApplicationService.update(id, requestDTO);

        return new ResponseEntity<>(response, OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<R> findById(UUID id) {
        R response = baseApplicationService.findById(id);

        return new ResponseEntity<>(response, OK);
    }

    @Override
    public ResponseEntity<Page<R>> findPageable(Integer page, Integer size) {
        Page<R> response = baseApplicationService.findPageable(page, size);

        return new ResponseEntity<>(response, OK);
    }

    @Override
    @PostMapping("/filtered")
    public ResponseEntity<Page<R>> findPageableAndFiltered(Integer page, Integer size, List<FilterCriteria> criteria) {
        Page<R> response = baseApplicationService.findPageableAndFiltered(page, size, criteria);

        return new ResponseEntity<>(response, OK);
    }

}

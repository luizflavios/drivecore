package br.com.drivecore.controller;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.core.generics.domain.model.IdReferenceGenericDTO;
import br.com.drivecore.domain.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.domain.employer.model.EmployerRequestDTO;
import br.com.drivecore.domain.employer.model.EmployerResponseDTO;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerApplicationService employerApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<IdReferenceGenericDTO> createEmployer(@RequestBody @Valid CreateEmployerRequestDTO createEmployerRequestDTO) {
        var employerIdResponseDTO = employerApplicationService.createEmployer(createEmployerRequestDTO);

        return new ResponseEntity<>(employerIdResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmployerResponseDTO> getEmployerById(@PathVariable UUID id) {
        var employerResponseDTO = employerApplicationService.getEmployerById(id);

        return ResponseEntity.ok(employerResponseDTO);
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EmployerResponseDTO>> getEmployersFiltered(@RequestBody(required = false) List<FilterCriteria> filters) {
        var employerResponseListDTO = employerApplicationService.getEmployersFiltered(filters);

        return ResponseEntity.ok(employerResponseListDTO);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmployerResponseDTO> updateEmployer(@PathVariable UUID id, @RequestBody EmployerRequestDTO employerRequestDTO) {
        var employerResponseDTO = employerApplicationService.updateEmployer(id, employerRequestDTO);

        return ResponseEntity.ok(employerResponseDTO);
    }

}

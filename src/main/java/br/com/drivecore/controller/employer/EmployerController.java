package br.com.drivecore.controller.employer;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.controller.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.controller.employer.model.EmployerResponseDTO;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
@Tag(name = "Employers")
public class EmployerController {

    private final EmployerApplicationService employerApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create employer with new username")
    public ResponseEntity<EmployerResponseDTO> createEmployer(@RequestBody @Valid CreateEmployerRequestDTO createEmployerRequestDTO) {
        EmployerResponseDTO employerResponseDTO = employerApplicationService.createEmployer(createEmployerRequestDTO);

        return new ResponseEntity<>(employerResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "List employers pageable and filterable")
    public ResponseEntity<Page<EmployerResponseDTO>> listEmployersPageable(@RequestParam int page,
                                                                           @RequestParam(required = false, defaultValue = "10") int size,
                                                                           @RequestBody(required = false) @Valid List<FilterCriteria> filterCriteria) {
        Page<EmployerResponseDTO> employersPageable = employerApplicationService.listEmployersPageable(page, size, filterCriteria);

        return new ResponseEntity<>(employersPageable, HttpStatus.CREATED);
    }

}

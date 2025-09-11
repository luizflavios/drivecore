package br.com.drivecore.controller.employer;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.controller.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.controller.employer.model.EmployerResponseDTO;
import br.com.drivecore.controller.employer.model.UpdateEmployerRequestDTO;
import br.com.drivecore.core.specification.model.FilterCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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

        return new ResponseEntity<>(employerResponseDTO, CREATED);
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "List employers pageable and filterable")
    public ResponseEntity<Page<EmployerResponseDTO>> listEmployersPageable(@RequestParam int page,
                                                                           @RequestParam(required = false, defaultValue = "10") int size,
                                                                           @RequestBody(required = false) @Valid List<FilterCriteria> filterCriteria) {
        Page<EmployerResponseDTO> employersPageable = employerApplicationService.listEmployersPageable(page, size, filterCriteria);

        return new ResponseEntity<>(employersPageable, CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYER')")
    @Operation(summary = "Update employer")
    public ResponseEntity<EmployerResponseDTO> updateEmployer(@PathVariable UUID id, @RequestBody UpdateEmployerRequestDTO updateEmployerRequestDTO) {
        EmployerResponseDTO employerResponseDTO = employerApplicationService.updateEmployer(id, updateEmployerRequestDTO);

        return new ResponseEntity<>(employerResponseDTO, OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYER')")
    @Operation(summary = "Get employer detail")
    public ResponseEntity<EmployerResponseDTO> getEmployerDetail(@PathVariable UUID id) {
        EmployerResponseDTO employerResponseDTO = employerApplicationService.getEmployerDetail(id);

        return new ResponseEntity<>(employerResponseDTO, OK);
    }


}

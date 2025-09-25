package br.com.drivecore.controller.employer;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.controller.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.controller.employer.model.EmployerResponseDTO;
import br.com.drivecore.controller.employer.model.UpdateEmployerRequestDTO;
import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
@Tag(name = "Employer")
public class EmployerController {

    private final EmployerApplicationService employerApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create employer")
    public ResponseEntity<EmployerResponseDTO> createEmployer(@RequestBody @Valid
                                                              CreateEmployerRequestDTO createEmployerRequestDTO) {
        EmployerResponseDTO employerResponseDTO = employerApplicationService.createEmployer(createEmployerRequestDTO);

        return new ResponseEntity<>(employerResponseDTO, CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get employer detail")
    public ResponseEntity<EmployerResponseDTO> getEmployerDetail(@PathVariable UUID id) {
        EmployerResponseDTO employerResponseDTO = employerApplicationService.getEmployerDetail(id);

        return new ResponseEntity<>(employerResponseDTO, OK);
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "List employers")
    public ResponseEntity<Page<EmployerResponseDTO>> getEmployers(@RequestBody @Valid FilteredAndPageableRequestDTO filters) {
        Page<EmployerResponseDTO> employerResponseDTOS = employerApplicationService.getEmployers(filters);

        return new ResponseEntity<>(employerResponseDTOS, OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update employer")
    public ResponseEntity<Void> updateEmployer(@PathVariable UUID id,
                                               @RequestBody @Valid UpdateEmployerRequestDTO updateEmployerRequestDTO) {
        employerApplicationService.updateEmployer(id, updateEmployerRequestDTO);

        return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete employer")
    public ResponseEntity<Void> deleteEmployer(@PathVariable UUID id) {
        employerApplicationService.deleteEmployer(id);

        return new ResponseEntity<>(NO_CONTENT);
    }

}

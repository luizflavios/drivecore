package br.com.drivecore.controller.employer;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.controller.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.controller.employer.model.EmployerResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerApplicationService employerApplicationService;

    @PostMapping
    public ResponseEntity<EmployerResponseDTO> createEmployer(@RequestBody @Valid CreateEmployerRequestDTO createEmployerRequestDTO) {
        EmployerResponseDTO employerResponseDTO = employerApplicationService.createEmployer(createEmployerRequestDTO);

        return new ResponseEntity<>(employerResponseDTO, HttpStatus.CREATED);
    }

}

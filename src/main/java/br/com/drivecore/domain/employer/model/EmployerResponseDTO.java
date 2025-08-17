package br.com.drivecore.domain.employer.model;

import br.com.drivecore.core.generics.domain.model.IdReferenceGenericDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployerResponseDTO {

    private String name;
    private String document;
    private LocalDate birthDate;
    private LocalDate admissionDate;
    private IdReferenceGenericDTO user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

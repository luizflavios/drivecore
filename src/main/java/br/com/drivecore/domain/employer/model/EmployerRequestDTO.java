package br.com.drivecore.domain.employer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployerRequestDTO {

    private String name;
    private String document;
    private LocalDate birthDate;
    private LocalDate admissionDate;

}

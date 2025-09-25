package br.com.drivecore.controller.employer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployerRequestDTO {

    private String socialNumber;

    private String email;

    private String fullName;

    private LocalDate birthDate;

    private LocalDate admissionDate;

}

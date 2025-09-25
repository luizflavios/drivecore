package br.com.drivecore.controller.employer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployerRequestDTO {

    @NotBlank
    private String socialNumber;

    @NotBlank
    private String email;

    @NotBlank
    private String fullName;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private LocalDate admissionDate;

}

package br.com.drivecore.controller.employer.model;

import br.com.drivecore.controller.authentication.model.CreateUserRequestDTO;
import jakarta.validation.Valid;
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
    private String name;

    @NotBlank
    private String document;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private LocalDate admissionDate;

    @Valid
    private CreateUserRequestDTO user;

}

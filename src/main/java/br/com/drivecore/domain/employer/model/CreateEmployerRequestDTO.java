package br.com.drivecore.domain.employer.model;

import br.com.drivecore.domain.user.model.CreateUserWithoutUsernameRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateEmployerRequestDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String document;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private LocalDate admissionDate;

    private CreateUserWithoutUsernameRequestDTO user;

}

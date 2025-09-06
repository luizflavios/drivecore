package br.com.drivecore.controller.authentication.model;

import br.com.drivecore.controller.model.ObjectReferenceDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    private String password;

    private Set<ObjectReferenceDTO> roles;

}

package br.com.drivecore.domain.user.model;

import br.com.drivecore.core.generics.IdReferenceGenericDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserRequestDTO {

    @NotBlank
    private String username;
    private String password;
    private List<IdReferenceGenericDTO> roles;

    @JsonIgnore
    private boolean tempPassword;

}

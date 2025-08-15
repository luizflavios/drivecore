package br.com.drivecore.domain.user.model;

import br.com.drivecore.core.generics.IdReferenceGenericDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserWithoutUsernameRequestDTO {
    private String password;
    private List<IdReferenceGenericDTO> roles;
}

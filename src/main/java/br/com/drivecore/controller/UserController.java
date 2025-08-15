package br.com.drivecore.controller;

import br.com.drivecore.application.user.UserApplicationService;
import br.com.drivecore.core.generics.IdReferenceGenericDTO;
import br.com.drivecore.domain.user.model.CreateUserRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserApplicationService userApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<IdReferenceGenericDTO> createUser(@RequestBody @Valid CreateUserRequestDTO createUserRequestDTO) {
        var userIdResponseDTO = userApplicationService.createUser(createUserRequestDTO);

        return new ResponseEntity<>(userIdResponseDTO, HttpStatus.CREATED);
    }

}

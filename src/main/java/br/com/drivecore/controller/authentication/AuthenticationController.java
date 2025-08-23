package br.com.drivecore.controller.authentication;

import br.com.drivecore.application.authentication.AuthenticationApplicationService;
import br.com.drivecore.controller.authentication.model.AuthenticationRequestDTO;
import br.com.drivecore.controller.authentication.model.AuthenticationResponseDTO;
import br.com.drivecore.controller.authentication.model.CreateUserRequestDTO;
import br.com.drivecore.controller.model.ObjectReferenceDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationApplicationService authenticationApplicationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Valid
                                                                  AuthenticationRequestDTO authenticationRequestDTO) {
        AuthenticationResponseDTO authenticationResponseDTO = authenticationApplicationService.authenticate(authenticationRequestDTO);

        return ResponseEntity.ok(authenticationResponseDTO);
    }

    @PostMapping("/users")
    public ResponseEntity<ObjectReferenceDTO> createUser(@RequestBody @Valid CreateUserRequestDTO createUserRequestDTO) {
        ObjectReferenceDTO objectReferenceDTO = authenticationApplicationService.createUser(createUserRequestDTO);

        return new ResponseEntity<>(objectReferenceDTO, CREATED);
    }


}

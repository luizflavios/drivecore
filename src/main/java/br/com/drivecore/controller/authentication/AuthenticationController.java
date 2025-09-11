package br.com.drivecore.controller.authentication;

import br.com.drivecore.application.authentication.AuthenticationApplicationService;
import br.com.drivecore.controller.authentication.model.*;
import br.com.drivecore.controller.machine.wheeling.truck.model.model.ObjectReferenceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationApplicationService authenticationApplicationService;

    @PostMapping
    @Operation(summary = "Get authentication by username and password")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Valid
                                                                  AuthenticationRequestDTO authenticationRequestDTO) {
        AuthenticationResponseDTO authenticationResponseDTO = authenticationApplicationService.authenticate(authenticationRequestDTO);

        return ResponseEntity.ok(authenticationResponseDTO);
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create user")
    public ResponseEntity<ObjectReferenceDTO> createUser(@RequestBody @Valid CreateUserRequestDTO createUserRequestDTO) {
        ObjectReferenceDTO objectReferenceDTO = authenticationApplicationService.createUser(createUserRequestDTO);

        return new ResponseEntity<>(objectReferenceDTO, CREATED);
    }

    @PatchMapping("/passwords")
    @Operation(summary = "Update password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        authenticationApplicationService.updatePassword(updatePasswordRequestDTO);

        return new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "List roles")
    public ResponseEntity<List<RoleResponseDTO>> listRoles() {
        List<RoleResponseDTO> roleResponseDTOList = authenticationApplicationService.listRoles();

        return new ResponseEntity<>(roleResponseDTOList, OK);
    }
}

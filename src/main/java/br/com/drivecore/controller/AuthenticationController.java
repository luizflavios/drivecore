package br.com.drivecore.controller;

import br.com.drivecore.application.authentication.AuthenticationApplicationService;
import br.com.drivecore.infrastructure.authentication.model.LoginRequestDTO;
import br.com.drivecore.infrastructure.authentication.model.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationApplicationService authenticationApplicationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authenticationApplicationService.authenticate(loginRequestDTO));
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validate() {
        log.info("Validado com sucesso");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

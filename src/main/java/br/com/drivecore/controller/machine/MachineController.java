package br.com.drivecore.controller.machine;

import br.com.drivecore.application.machine.MachineApplicationService;
import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/machines")
@RequiredArgsConstructor
@Tag(name = "Machine")
public class MachineController {

    private final MachineApplicationService machineApplicationService;

    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "List machines")
    public ResponseEntity<Page<MachineResponseDTO>> getMachines(@RequestBody @Valid FilteredAndPageableRequestDTO filters) {
        Page<MachineResponseDTO> machineResponseDTOS = machineApplicationService.getMachines(filters);

        return new ResponseEntity<>(machineResponseDTOS, OK);
    }

}

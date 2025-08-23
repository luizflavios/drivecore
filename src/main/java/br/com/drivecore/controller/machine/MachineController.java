package br.com.drivecore.controller.machine;

import br.com.drivecore.application.machine.MachineApplicationService;
import br.com.drivecore.controller.machine.model.CreateMachineRequestDTO;
import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/machines")
@RequiredArgsConstructor
public class MachineController {

    private final MachineApplicationService machineApplicationService;

    @PostMapping
    public ResponseEntity<MachineResponseDTO> createMachine(@RequestBody @Valid CreateMachineRequestDTO createMachineRequestDTO) {
        MachineResponseDTO machineResponseDTO = machineApplicationService.createMachine(createMachineRequestDTO);

        return new ResponseEntity<>(machineResponseDTO, HttpStatus.CREATED);
    }

}

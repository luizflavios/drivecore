package br.com.drivecore.controller.machine.model;

import br.com.drivecore.domain.machine.enums.MachineType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MachineResponseDTO {

    private UUID id;
    private MachineType machineType;

}

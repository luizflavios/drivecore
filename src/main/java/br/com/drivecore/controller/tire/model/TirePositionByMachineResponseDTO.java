package br.com.drivecore.controller.tire.model;

import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TirePositionByMachineResponseDTO {

    private MachineResponseDTO machine;

    private List<TirePositionResponseDTO> tiresPositions;

}

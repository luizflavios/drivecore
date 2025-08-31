package br.com.drivecore.controller.tire.model;

import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import br.com.drivecore.domain.tire.enums.TirePositionSide;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TirePositionResponseDTO {

    private UUID id;

    private TireResponseDTO tire;

    private MachineResponseDTO machine;

    private TirePositionSide side;

    private int axle;

    private LocalDateTime createdAt;

}

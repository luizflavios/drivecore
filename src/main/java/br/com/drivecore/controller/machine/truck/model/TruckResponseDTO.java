package br.com.drivecore.controller.machine.truck.model;

import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TruckResponseDTO extends MachineResponseDTO {

    private UUID id;

    private Integer axles;

    private List<MachineResponseDTO> equipments;

    private LocalDateTime createdAt;
}

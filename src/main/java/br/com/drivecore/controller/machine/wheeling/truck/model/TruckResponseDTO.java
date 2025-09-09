package br.com.drivecore.controller.machine.wheeling.truck.model;

import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TruckResponseDTO extends MachineResponseDTO {

    private String licensePlate;
    private int year;
    private int horsePower;

}

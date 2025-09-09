package br.com.drivecore.controller.machine.wheeling.truck.model;

import br.com.drivecore.controller.machine.model.CreateMachineRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTruckRequestDTO extends CreateMachineRequestDTO {

    @NotBlank
    private String licensePlate;

    private int year;

    private int horsePower;

}

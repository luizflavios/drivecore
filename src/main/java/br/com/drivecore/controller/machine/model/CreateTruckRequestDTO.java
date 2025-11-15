package br.com.drivecore.controller.machine.model;

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

    private long mileage;

    private int horsePower;

    private int axles;

}

package br.com.drivecore.controller.machine.model;

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
    private int modelYear;
    private int horsePower;
    private long mileage;
    private int axles;

}

package br.com.drivecore.controller.machine.model;

import br.com.drivecore.domain.machine.enums.MachineStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTruckRequestDTO {

    private String brand;
    private String model;
    private BigDecimal paidAmount;
    private LocalDate purchaseDate;
    private String licensePlate;
    private int modelYear;
    private long mileage;
    private int manufactureYear;
    private int horsePower;
    private int axles;
    private MachineStatus machineStatus;

}

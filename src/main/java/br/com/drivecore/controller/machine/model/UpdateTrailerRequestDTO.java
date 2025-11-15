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
public class UpdateTrailerRequestDTO {

    private String licensePlate;

    private Long mileage;

    private BigDecimal lengthMeters;

    private BigDecimal widthMeters;

    private BigDecimal heightMeters;

    private BigDecimal tareWeightKg;

    private BigDecimal maxPayloadKg;

    private int axles;

    private int modelYear;

    private MachineStatus machineStatus;

    private String brand;

    private String model;

    private BigDecimal paidAmount;

    private LocalDate purchaseDate;

}

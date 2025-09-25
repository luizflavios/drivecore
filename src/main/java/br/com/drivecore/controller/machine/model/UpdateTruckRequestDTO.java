package br.com.drivecore.controller.machine.model;

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
    private int manufactureYear;
    private int horsePower;

}

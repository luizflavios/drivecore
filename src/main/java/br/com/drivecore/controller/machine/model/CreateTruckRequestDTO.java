package br.com.drivecore.controller.machine.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateTruckRequestDTO {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    private BigDecimal paidAmount;

    @NotNull
    private LocalDate purchaseDate;

    @NotBlank
    private String licensePlate;

    @NotNull
    private int modelYear;

    @NotNull
    private int manufactureYear;

    @NotNull
    private long mileage;

    private int horsePower;

}

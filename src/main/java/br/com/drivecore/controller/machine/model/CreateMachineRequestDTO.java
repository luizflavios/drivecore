package br.com.drivecore.controller.machine.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMachineRequestDTO {

    private String brand;

    private String model;

    private BigDecimal paidAmount;

    @NotBlank
    private String licensePlate;

    private Integer modelYear;

    private int manufactureYear;

}

package br.com.drivecore.controller.tire.model;

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
public class CreateTireRequestDTO {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    private BigDecimal paidAmount;

    @NotNull
    private LocalDate purchaseDate;

    @NotNull
    private Long mileage;

    private LocalDate warrantyDate;


    private Integer reconditioning;


}

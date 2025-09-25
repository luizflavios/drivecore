package br.com.drivecore.controller.tire.model;

import br.com.drivecore.domain.tire.enums.TireCondition;
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
    private String fireCode;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String size;

    @NotNull
    private TireCondition tireCondition;

    @NotNull
    private BigDecimal price;

    @NotNull
    private LocalDate purchaseDate;

    private int retreading;

}

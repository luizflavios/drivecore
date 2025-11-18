package br.com.drivecore.controller.tire.model;

import br.com.drivecore.domain.tire.enums.TireCondition;
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

    private LocalDate purchaseDate;

    private int manufactureYear;

    private String manufacturer;

    private String fireCode;

    private String observation;

    private TireCondition tireCondition;

    private BigDecimal price;

    private Long mileage;

}

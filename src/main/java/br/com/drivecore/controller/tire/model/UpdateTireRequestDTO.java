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
public class UpdateTireRequestDTO {

    private String fireCode;

    private String brand;

    private String model;

    private String size;

    private TireCondition tireCondition;

    private BigDecimal price;

    private LocalDate purchaseDate;

    private int retreading;

}

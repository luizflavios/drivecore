package br.com.drivecore.controller.tire.model;

import br.com.drivecore.domain.tire.enums.TireCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TireResponseDTO {

    private UUID id;
    private String fireCode;
    private String brand;
    private String model;
    private String size;
    private TireCondition tireCondition;
    private BigDecimal price;
    private LocalDate purchaseDate;
    private Long mileage;
    private Long totalMileage;
    private int retreadingCount;
    private LocalDateTime createdAt;

}

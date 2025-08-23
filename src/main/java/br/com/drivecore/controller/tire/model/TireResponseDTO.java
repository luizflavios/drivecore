package br.com.drivecore.controller.tire.model;

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
    private String brand;
    private String model;
    private BigDecimal paidAmount;
    private LocalDate purchaseDate;
    private Long mileage;
    private LocalDate warrantyDate;
    private Integer reconditioning;
    private LocalDateTime createdAt;

}

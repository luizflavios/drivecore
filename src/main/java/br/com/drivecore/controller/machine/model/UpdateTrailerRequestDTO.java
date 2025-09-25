package br.com.drivecore.controller.machine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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

}

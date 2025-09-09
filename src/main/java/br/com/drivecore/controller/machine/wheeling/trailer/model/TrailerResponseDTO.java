package br.com.drivecore.controller.machine.wheeling.trailer.model;

import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import br.com.drivecore.domain.machine.wheeling.trailer.enums.TrailerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrailerResponseDTO extends MachineResponseDTO {

    private String licensePlate;

    private TrailerType trailerType;

    private BigDecimal lengthMeters;

    private BigDecimal widthMeters;

    private BigDecimal heightMeters;

    private BigDecimal tareWeightKg;

    private BigDecimal maxPayloadKg;

}

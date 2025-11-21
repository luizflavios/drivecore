package br.com.drivecore.controller.tire.model;

import br.com.drivecore.domain.tire.enums.TireSide;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TirePositionResponseDTO {

    private UUID id;

    private SummaryTireResponseDTO tire;

    private int axle;

    private TireSide side;

}

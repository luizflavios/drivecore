package br.com.drivecore.controller.tire.model;

import br.com.drivecore.controller.machine.truck.model.TruckResponseDTO;
import br.com.drivecore.domain.tire.enums.TirePositionSide;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TirePositionResponseDTO {

    private TireResponseDTO tire;

    private TruckResponseDTO truck;

    private TirePositionSide side;

    private int axle;

}

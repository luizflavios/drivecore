package br.com.drivecore.controller.tire.model;

import br.com.drivecore.controller.machine.wheeling.truck.model.model.ObjectReferenceDTO;
import br.com.drivecore.domain.tire.enums.TirePositionSide;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTirePositionDTO {

    @NotNull
    private ObjectReferenceDTO tire;

    @NotNull
    private ObjectReferenceDTO machine;

    @NotNull
    private TirePositionSide side;

    @Min(value = 1)
    @Max(value = 9)
    private int axle;


}

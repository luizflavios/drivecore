package br.com.drivecore.controller.machine.truck.model;

import br.com.drivecore.controller.machine.model.CreateMachineRequestDTO;
import br.com.drivecore.controller.model.ObjectReferenceDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTruckRequestDTO {

    @NotNull
    private CreateMachineRequestDTO machine;

    @NotNull
    private Integer axles;

    private List<ObjectReferenceDTO> equipments;

}

package br.com.drivecore.controller.machine.truck.model;

import br.com.drivecore.controller.machine.model.CreateMachineRequestDTO;
import br.com.drivecore.controller.model.ObjectReferenceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTruckRequestDTO extends CreateMachineRequestDTO {

    private List<ObjectReferenceDTO> equipments;

}

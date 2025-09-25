package br.com.drivecore.controller.tire.model;

import br.com.drivecore.controller.model.ObjectReferenceDTO;
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

    private ObjectReferenceDTO tire;

    private int axle;

    private int side;

}

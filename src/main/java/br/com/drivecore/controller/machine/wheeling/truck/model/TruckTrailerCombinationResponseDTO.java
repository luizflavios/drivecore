package br.com.drivecore.controller.machine.wheeling.truck.model;


import br.com.drivecore.controller.machine.wheeling.truck.model.model.ObjectReferenceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TruckTrailerCombinationResponseDTO {

    private UUID id;

    private LocalDateTime createdAt;

    private LocalDateTime finishedAt;

    private ObjectReferenceDTO truck;

    private ObjectReferenceDTO trailer;

    private Boolean trailerAlreadyInUse;

    public TruckTrailerCombinationResponseDTO(Boolean trailerAlreadyInUse) {
        this.trailerAlreadyInUse = trailerAlreadyInUse;
    }

}

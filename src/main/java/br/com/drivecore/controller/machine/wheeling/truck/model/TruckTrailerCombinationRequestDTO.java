package br.com.drivecore.controller.machine.wheeling.truck.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TruckTrailerCombinationRequestDTO {

    @NotNull
    private UUID truckId;

    @NotNull
    private UUID trailerId;

}

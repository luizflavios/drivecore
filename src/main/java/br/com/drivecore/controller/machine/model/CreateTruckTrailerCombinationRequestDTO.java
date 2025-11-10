package br.com.drivecore.controller.machine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTruckTrailerCombinationRequestDTO {

    private UUID truckId;
    private UUID trailerId;

}

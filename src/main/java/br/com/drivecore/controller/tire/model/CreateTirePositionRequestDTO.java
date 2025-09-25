package br.com.drivecore.controller.tire.model;

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
public class CreateTirePositionRequestDTO {

    @NotNull
    private UUID machineId;

    @NotNull
    private UUID tireId;

    @NotNull
    private int axle; // 0 - 8

    @NotNull
    private int side; // 0 - external_left, 1 - internal_left, 2 - internal_right, 3 - external_right

}

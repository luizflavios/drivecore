package br.com.drivecore.controller.machine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TruckTrailerCombinationResponseDTO {

    private UUID id;

    private TruckResponseDTO truck;

    private TrailerResponseDTO trailer;

    private LocalDateTime createdAt;

    private LocalDateTime finishedAt;

}

package br.com.drivecore.controller.tire.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TirePositionByMachineResponseDTO {

    private List<TirePositionResponseDTO> tiresPositions;

}

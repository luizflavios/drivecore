package br.com.drivecore.controller.contract.delivery.model;

import br.com.drivecore.controller.contract.model.ContractResponseDTO;
import br.com.drivecore.controller.machine.wheeling.truck.model.TruckResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDTO extends ContractResponseDTO {

    private UUID id;

    private String destiny;

    private LocalDate startDate;

    private LocalDate finalDate;

    private Long initialKilometer;

    private Long finalKilometer;

    private Set<TruckResponseDTO> trucks;

    private LocalDateTime createdAt;

}

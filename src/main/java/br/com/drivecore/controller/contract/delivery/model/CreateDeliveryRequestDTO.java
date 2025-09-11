package br.com.drivecore.controller.contract.delivery.model;

import br.com.drivecore.controller.contract.model.CreateContractRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDeliveryRequestDTO extends CreateContractRequestDTO {

    @NotBlank
    private String destiny;

    private LocalDate startDate;

    private LocalDate finalDate;

    private Long initialKilometer;

    private Long finalKilometer;

    private UUID truckId;

}

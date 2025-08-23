package br.com.drivecore.controller.contract.delivery.model;

import br.com.drivecore.controller.contract.model.CreateContractRequestDTO;
import br.com.drivecore.controller.model.ObjectReferenceDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDeliveryRequestDTO {

    @NotBlank
    private String destiny;

    private LocalDate startDate;

    private LocalDate finalDate;

    private Long initialKilometer;

    private Long finalKilometer;

    private Set<ObjectReferenceDTO> trucks;

    private CreateContractRequestDTO contract;

}

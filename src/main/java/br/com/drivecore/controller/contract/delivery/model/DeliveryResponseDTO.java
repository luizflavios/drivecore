package br.com.drivecore.controller.contract.delivery.model;

import br.com.drivecore.controller.contract.model.AttachmentDownloadResponseDTO;
import br.com.drivecore.controller.contract.model.ContractResponseDTO;
import br.com.drivecore.controller.contract.model.CreateContractExpenseResponseDTO;
import br.com.drivecore.controller.machine.model.SummaryTruckTrailerCombinationResponseDTO;
import br.com.drivecore.domain.contract.delivery.enums.DeliveryStatus;
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
public class DeliveryResponseDTO extends ContractResponseDTO {

    private String destiny;

    private String origin;

    private LocalDate startDate;

    private LocalDate finalDate;

    private Long initialMileage;

    private Long finalMileage;

    private DeliveryStatus deliveryStatus;

    private SummaryTruckTrailerCombinationResponseDTO truckTrailerCombination;

    private Set<CreateContractExpenseResponseDTO> expenses;

    private Set<AttachmentDownloadResponseDTO> attachments;

}

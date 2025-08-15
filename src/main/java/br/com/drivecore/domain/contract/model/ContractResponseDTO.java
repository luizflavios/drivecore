package br.com.drivecore.domain.contract.model;

import br.com.drivecore.domain.employer.model.EmployerSummaryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractResponseDTO {

    private UUID id;
    private String destiny;
    private LocalDate startDate;
    private LocalDate finalDate;
    private Long initialKilometer;
    private Long finalKilometer;
    private BigDecimal commission;
    private BigDecimal contractValue;
    private EmployerSummaryResponseDTO responsible;

}

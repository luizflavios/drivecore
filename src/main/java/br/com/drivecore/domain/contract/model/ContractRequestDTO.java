package br.com.drivecore.domain.contract.model;

import br.com.drivecore.core.generics.IdReferenceGenericDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractRequestDTO {

    private String destiny;
    private LocalDate startDate;
    private LocalDate finalDate;
    private Long initialKilometer;
    private Long finalKilometer;
    private BigDecimal commission;
    private BigDecimal contractValue;
    private IdReferenceGenericDTO responsible;

}

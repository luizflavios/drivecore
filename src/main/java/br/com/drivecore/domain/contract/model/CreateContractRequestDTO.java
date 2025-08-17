package br.com.drivecore.domain.contract.model;

import br.com.drivecore.core.generics.domain.model.IdReferenceGenericDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateContractRequestDTO {

    @NotBlank
    private String destiny;

    private LocalDate startDate;

    private LocalDate finalDate;

    private Long initialKilometer;

    private Long finalKilometer;

    private BigDecimal commission;

    @NotNull
    private BigDecimal contractValue;

    private IdReferenceGenericDTO responsible;

}

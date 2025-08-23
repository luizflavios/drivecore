package br.com.drivecore.controller.contract.model;

import br.com.drivecore.controller.contract.enums.ContractType;
import br.com.drivecore.controller.model.ObjectReferenceDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateContractRequestDTO {

    private BigDecimal commission;

    @NotNull
    private BigDecimal contractValue;

    private List<ObjectReferenceDTO> responsible;

    private ContractType type;

}

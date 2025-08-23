package br.com.drivecore.controller.contract.model;

import br.com.drivecore.controller.employer.model.EmployerResponseDTO;
import br.com.drivecore.controller.expense.model.ExpenseResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractResponseDTO {

    private UUID id;

    private BigDecimal commission;

    private BigDecimal contractValue;

    private List<EmployerResponseDTO> responsible;

    private List<ExpenseResponseDTO> expenses;

    private LocalDateTime createdAt;
    
}

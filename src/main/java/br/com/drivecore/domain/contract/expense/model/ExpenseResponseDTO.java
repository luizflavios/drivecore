package br.com.drivecore.domain.contract.expense.model;

import br.com.drivecore.core.generics.domain.model.IdReferenceGenericDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponseDTO {

    private UUID id;
    private String description;
    private BigDecimal amount;
    private IdReferenceGenericDTO createdBy;
    private LocalDateTime createdAt;
    
}

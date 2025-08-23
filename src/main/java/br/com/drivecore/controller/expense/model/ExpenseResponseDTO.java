package br.com.drivecore.controller.expense.model;

import br.com.drivecore.controller.model.ObjectReferenceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpenseResponseDTO {

    private UUID id;
    private BigDecimal amount;
    private String description;
    private ObjectReferenceDTO createdBy;

}

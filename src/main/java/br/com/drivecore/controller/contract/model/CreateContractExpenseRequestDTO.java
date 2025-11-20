package br.com.drivecore.controller.contract.model;

import br.com.drivecore.domain.contract.enums.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateContractExpenseRequestDTO {
    private ExpenseType expenseType;
    private LocalDate date;
    private Double amount;
    private String description;
}

package br.com.drivecore.controller.expense.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateExpenseRequestDTO {

    @NotNull
    private BigDecimal amount;
    @NotBlank
    private String description;


}

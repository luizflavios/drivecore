package br.com.drivecore.infrastructure.persistence.expense.entities;

import br.com.drivecore.domain.contract.enums.ExpenseType;
import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ExpenseEntity extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "expense_type")
    private ExpenseType expenseType;

}

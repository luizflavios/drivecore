package br.com.drivecore.infrastructure.persistence.contract.entities;

import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import br.com.drivecore.infrastructure.persistence.contract.expense.entities.ExpenseEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "contracts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ContractEntity extends BaseEntity {

    @Column(nullable = false)
    private String destiny;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "final_date")
    private LocalDate finalDate;

    @Column(name = "initial_kilometer")
    private Long initialKilometer;

    @Column(name = "final_kilometer")
    private Long finalKilometer;

    @Column(nullable = false)
    private BigDecimal commission;

    @Column(nullable = false, name = "contract_Value")
    private BigDecimal contractValue;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private EmployerEntity responsible;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contracts_expenses",
            joinColumns = @JoinColumn(name = "contract_id", referencedColumnName = "id", table = "contracts"),
            inverseJoinColumns = @JoinColumn(name = "expense_id", referencedColumnName = "id", table = "expenses"))
    private Set<ExpenseEntity> expenses;

    @Version
    private Long version;
}

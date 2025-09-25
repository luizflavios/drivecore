package br.com.drivecore.infrastructure.persistence.contract.entities;

import br.com.drivecore.domain.contract.enums.ContractType;
import br.com.drivecore.infrastructure.persistence.attachment.entities.AttachmentEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.expense.entities.ExpenseEntity;
import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "contracts")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ContractEntity extends BaseEntity {

    private BigDecimal commission;

    @Column(nullable = false, name = "contract_value")
    private BigDecimal contractValue;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private EmployerEntity createdBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contracts_responsible",
            joinColumns = @JoinColumn(name = "contract_id", referencedColumnName = "id", table = "contracts"),
            inverseJoinColumns = @JoinColumn(name = "responsible_id", referencedColumnName = "id", table = "employers"))
    private Set<EmployerEntity> responsible;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "contracts_expenses",
            joinColumns = @JoinColumn(name = "contract_id", referencedColumnName = "id", table = "contracts"),
            inverseJoinColumns = @JoinColumn(name = "expense_id", referencedColumnName = "id", table = "expenses"))
    private Set<ExpenseEntity> expenses;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contracts_attachments",
            joinColumns = @JoinColumn(name = "contract_id", referencedColumnName = "id", table = "contracts"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id", referencedColumnName = "id", table = "attachments"))
    private Set<AttachmentEntity> attachments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractType type;

}

package br.com.drivecore.infrastructure.persistence.contract.entities;

import br.com.drivecore.core.generics.GenericEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ContractEntity extends GenericEntity {

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

    @Version
    private Long version;
}

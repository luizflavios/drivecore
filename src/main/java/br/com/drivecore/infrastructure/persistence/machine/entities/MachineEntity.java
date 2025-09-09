package br.com.drivecore.infrastructure.persistence.machine.entities;

import br.com.drivecore.domain.machine.enums.MachineType;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "machines")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MachineEntity extends BaseEntity {

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(nullable = false, name = "purchase_date")
    private LocalDate purchaseDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private MachineType machineType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private EmployerEntity createdBy;

}

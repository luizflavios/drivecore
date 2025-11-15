package br.com.drivecore.infrastructure.persistence.machine.entities;

import br.com.drivecore.domain.machine.enums.MachineStatus;
import br.com.drivecore.domain.machine.enums.MachineType;
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
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MachineEntity extends BaseEntity {

    private String brand;

    private String model;

    @Column(name = "model_year")
    private int modelYear;

    @Column(name = "manufacture_year")
    private int manufactureYear;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "machine_type")
    private MachineType machineType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "machine_status")
    private MachineStatus machineStatus;

}

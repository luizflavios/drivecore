package br.com.drivecore.infrastructure.persistence.tire.entities;

import br.com.drivecore.domain.tire.enums.TirePositionSide;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tires_positions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TirePositionEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "tire_id", nullable = false)
    private TireEntity tire;

    @ManyToOne
    @JoinColumn(name = "machine_id", nullable = false)
    private MachineEntity machine;

    private TirePositionSide side;

    private int axle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private EmployerEntity createdBy;

    @Column(name = "in_use")
    private Boolean inUse;

}

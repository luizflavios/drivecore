package br.com.drivecore.infrastructure.persistence.tire.entities;

import br.com.drivecore.domain.tire.enums.TirePositionSide;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import br.com.drivecore.infrastructure.persistence.machine.truck.entities.TruckEntity;
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

    @OneToOne
    @JoinColumn(name = "tire_id", nullable = false)
    private TireEntity tire;

    @ManyToOne
    @JoinColumn(name = "truck_id", nullable = false)
    private TruckEntity truck;

    private TirePositionSide side;

    private int axle;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private EmployerEntity createdBy;

}

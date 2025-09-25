package br.com.drivecore.infrastructure.persistence.tire.entities;

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
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class TirePositionEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(nullable = false, name = "tire_id")
    private TireEntity tire;

    @ManyToOne
    @JoinColumn(nullable = false, name = "machine_id")
    private MachineEntity machine;

    @Column(nullable = false)
    private Boolean inUse;

    @Column(nullable = false)
    private int axle; // 0 - 8

    @Column(nullable = false)
    private int side; // 0 - external_left, 1 - internal_left, 2 - internal_right, 3 - external_right

}

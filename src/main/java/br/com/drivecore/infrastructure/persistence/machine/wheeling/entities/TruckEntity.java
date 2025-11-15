package br.com.drivecore.infrastructure.persistence.machine.wheeling.entities;

import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name = "trucks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class TruckEntity extends MachineEntity {

    @Column(nullable = false, name = "license_plate")
    private String licensePlate;

    @Column(name = "horse_power", nullable = false)
    private int horsePower;

    @Column(nullable = false)
    private long mileage;

    @Column(nullable = false)
    private int axles;

    @OneToMany(mappedBy = "truck", fetch = FetchType.LAZY)
    private Set<TruckTrailerCombinationEntity> combinations;

}

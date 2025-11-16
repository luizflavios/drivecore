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

    @Column(nullable = false)
    private long mileage;

    @Column(nullable = false)
    private int axles;

    private String chassi;

    private String renavam;

    @Column(name = "current_year_ipva")
    private int currentYearIpva;

    @OneToMany(mappedBy = "truck", fetch = FetchType.LAZY)
    private Set<TruckTrailerCombinationEntity> combinations;

}

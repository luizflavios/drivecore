package br.com.drivecore.infrastructure.persistence.machine.entities;

import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "trucks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TruckEntity extends BaseEntity {

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "machine_id", nullable = false)
    private MachineEntity machine;

    @Column(nullable = false)
    private Integer axles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trucks_equipments",
            joinColumns = @JoinColumn(name = "truck_id", referencedColumnName = "id", table = "trucks"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id", table = "machines"))
    private List<MachineEntity> equipments;

}

package br.com.drivecore.infrastructure.persistence.machine.wheeling.entities;

import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "trucks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TruckEntity extends MachineEntity {

    @Column(nullable = false, name = "license_plate")
    private String licensePlate;

    private int year;

    @Column(name = "horse_power")
    private int horsePower;

}

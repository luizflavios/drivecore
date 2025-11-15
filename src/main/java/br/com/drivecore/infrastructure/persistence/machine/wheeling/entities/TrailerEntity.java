package br.com.drivecore.infrastructure.persistence.machine.wheeling.entities;

import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "trailers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class TrailerEntity extends MachineEntity {

    private int axles;

    @Column(nullable = false, name = "license_plate")
    private String licensePlate;

    @Column(nullable = false)
    private Long mileage;

    @Column(name = "length_meters")
    private BigDecimal lengthMeters;

    @Column(name = "width_meters")
    private BigDecimal widthMeters;

    @Column(name = "height_meters")
    private BigDecimal heightMeters;

}

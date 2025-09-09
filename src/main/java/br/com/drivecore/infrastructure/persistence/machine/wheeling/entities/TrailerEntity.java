package br.com.drivecore.infrastructure.persistence.machine.wheeling.entities;

import br.com.drivecore.domain.machine.wheeling.trailer.enums.TrailerType;
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
@EqualsAndHashCode(callSuper = true)
public class TrailerEntity extends MachineEntity {

    @Column(nullable = false, name = "license_plate")
    private String licensePlate;

    @Column(nullable = false, name = "type")
    private TrailerType trailerType;

    @Column(name = "length_meters")
    private BigDecimal lengthMeters;

    @Column(name = "width_meters")
    private BigDecimal widthMeters;

    @Column(name = "height_meters")
    private BigDecimal heightMeters;

    @Column(name = "tare_weight_kg")
    private BigDecimal tareWeightKg;

    @Column(name = "max_payload_kg")
    private BigDecimal maxPayloadKg;

}

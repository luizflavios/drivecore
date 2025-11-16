package br.com.drivecore.infrastructure.persistence.machine.wheeling.entities;

import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @Column(nullable = false)
    private Long mileage;

    private String chassi;

    private String renavam;

    @Column(name = "current_year_ipva")
    private int currentYearIpva;


}

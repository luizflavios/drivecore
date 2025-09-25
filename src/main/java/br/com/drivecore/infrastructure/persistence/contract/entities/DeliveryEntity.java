package br.com.drivecore.infrastructure.persistence.contract.entities;

import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "deliveries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DeliveryEntity extends ContractEntity {

    @Column(nullable = false)
    private String destiny;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "final_date")
    private LocalDate finalDate;

    @Column(name = "initial_kilometer")
    private Long initialKilometer;

    @Column(name = "final_kilometer")
    private Long finalKilometer;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private TruckEntity truck;
}

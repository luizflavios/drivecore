package br.com.drivecore.infrastructure.persistence.contract.entities;

import br.com.drivecore.infrastructure.persistence.machine.truck.entities.TruckEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "deliveries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "deliveries_trucks",
            joinColumns = @JoinColumn(name = "delivery_id", referencedColumnName = "id", table = "deliveries"),
            inverseJoinColumns = @JoinColumn(name = "truck_id", referencedColumnName = "id", table = "trucks"))
    private Set<TruckEntity> trucks;

}

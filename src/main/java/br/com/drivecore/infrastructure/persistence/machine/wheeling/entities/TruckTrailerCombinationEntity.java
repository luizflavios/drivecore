package br.com.drivecore.infrastructure.persistence.machine.wheeling.entities;

import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "trucks_trailers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TruckTrailerCombinationEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "truck_id", nullable = false)
    private TruckEntity truck;

    @ManyToOne
    @JoinColumn(name = "trailer_id", nullable = false)
    private TrailerEntity trailer;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

}

package br.com.drivecore.infrastructure.persistence.tire.entities;

import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tires_retreadings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class TireRetreadingEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(nullable = false, name = "tire_id")
    private TireEntity tire;

    @Column(nullable = false)
    private Long mileage;

}

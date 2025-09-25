package br.com.drivecore.infrastructure.persistence.tire.entities;

import br.com.drivecore.domain.tire.enums.TireCondition;
import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "tires")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class TireEntity extends BaseEntity {

    @Column(name = "fire_code", nullable = false, unique = true)
    private String fireCode;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String size;

    @Enumerated(EnumType.STRING)
    @Column(name = "tire_condition", nullable = false)
    private TireCondition tireCondition;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(nullable = false)
    private Long mileage;

    @OneToMany(mappedBy = "tire", fetch = FetchType.EAGER)
    private Set<TireRetreadingEntity> retreads;

}

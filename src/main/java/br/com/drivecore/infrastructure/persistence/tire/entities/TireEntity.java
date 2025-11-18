package br.com.drivecore.infrastructure.persistence.tire.entities;

import br.com.drivecore.domain.tire.enums.TireCondition;
import br.com.drivecore.domain.tire.enums.TireStatus;
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

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "manufacture_year")
    private int manufactureYear;

    private String manufacturer;

    @Column(name = "fire_code", unique = true)
    private String fireCode;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(name = "tire_condition", nullable = false)
    private TireCondition tireCondition;

    @Enumerated(EnumType.STRING)
    @Column(name = "tire_status", nullable = false)
    private TireStatus tireStatus;

    private BigDecimal price;

    private Long mileage;

    @OneToMany(mappedBy = "tire", fetch = FetchType.EAGER)
    private Set<TireHistoryEntity> history;

}

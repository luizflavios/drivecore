package br.com.drivecore.infrastructure.persistence.tire.entities;

import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tires")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TireEntity extends BaseEntity {

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(nullable = false, name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(nullable = false)
    private Long mileage;

    @Column(name = "warranty_date")
    private LocalDate warrantyDate;

    private Integer reconditioning;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private EmployerEntity createdBy;

}

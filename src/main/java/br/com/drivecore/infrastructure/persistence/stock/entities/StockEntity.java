package br.com.drivecore.infrastructure.persistence.stock.entities;

import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Version;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

//@Entity
//@Table(name = "stocks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StockEntity extends BaseEntity {

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "location")
    private String location;

    @Version
    private Long version;

}

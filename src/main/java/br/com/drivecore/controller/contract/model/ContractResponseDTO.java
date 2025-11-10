package br.com.drivecore.controller.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractResponseDTO {

    private UUID id;

    private BigDecimal commission;

    private BigDecimal contractValue;

    private LocalDateTime createdAt;

}

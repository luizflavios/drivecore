package br.com.drivecore.controller.machine.model;

import br.com.drivecore.domain.machine.enums.MachineType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MachineResponseDTO {

    private UUID id;
    private String brand;
    private String model;
    private BigDecimal paidAmount;
    private LocalDate purchaseDate;
    private MachineType machineType;
    private LocalDateTime createdAt;

}

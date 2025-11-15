package br.com.drivecore.controller.machine.model;

import br.com.drivecore.domain.machine.enums.MachineStatus;
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
    private MachineType machineType;
    private String brand;
    private String model;
    private int modelYear;
    private int manufactureYear;
    private MachineStatus machineStatus;
    private BigDecimal paidAmount;
    private LocalDate purchaseDate;
    private LocalDateTime createdAt;


    public MachineResponseDTO(UUID id, MachineType machineType) {
        this.id = id;
        this.machineType = machineType;
    }

}

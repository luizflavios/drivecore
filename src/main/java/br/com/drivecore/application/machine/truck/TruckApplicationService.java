package br.com.drivecore.application.machine.truck;

import br.com.drivecore.application.machine.MachineApplicationService;
import br.com.drivecore.controller.machine.truck.model.CreateTruckRequestDTO;
import br.com.drivecore.controller.machine.truck.model.TruckResponseDTO;
import br.com.drivecore.domain.machine.truck.TruckService;
import br.com.drivecore.domain.machine.truck.mapper.TruckMapper;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import br.com.drivecore.infrastructure.persistence.machine.truck.entities.TruckEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TruckApplicationService {

    private final MachineApplicationService machineApplicationService;

    private final TruckService truckService;

    @Transactional
    public TruckResponseDTO createTruck(CreateTruckRequestDTO createTruckRequestDTO) {
        MachineEntity machine = machineApplicationService.createDomainMachine(createTruckRequestDTO.getMachine());

        TruckEntity truckEntity = TruckMapper.INSTANCE.toEntity(createTruckRequestDTO, machine);

        truckService.createTruck(truckEntity);

        log.info("Truck {} successfully create by - {}", truckEntity.getId(), truckEntity.getMachine().getCreatedBy().getId());

        return TruckMapper.INSTANCE.toTruckResponseDTO(truckEntity);
    }

}

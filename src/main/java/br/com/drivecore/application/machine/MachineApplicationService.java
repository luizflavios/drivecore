package br.com.drivecore.application.machine;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.controller.machine.model.CreateMachineRequestDTO;
import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import br.com.drivecore.domain.machine.MachineService;
import br.com.drivecore.domain.machine.mapper.MachineMapper;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MachineApplicationService {

    private final EmployerApplicationService employerApplicationService;

    private final MachineService machineService;

    public MachineResponseDTO createMachine(CreateMachineRequestDTO createMachineRequestDTO) {
        MachineEntity machine = createDomainMachine(createMachineRequestDTO);

        log.info("Machine {} successfully created by - {}", machine.getId(), machine.getCreatedBy().getId());

        return MachineMapper.INSTANCE.toMachineResponseDTO(machine);
    }

    public MachineEntity createDomainMachine(CreateMachineRequestDTO createMachineRequestDTO) {
        EmployerEntity loggedEmployer = employerApplicationService.getLoggedEmployer();

        MachineEntity machine = MachineMapper.INSTANCE.toEntity(createMachineRequestDTO, loggedEmployer);

        machineService.createMachine(machine);

        return machine;
    }

}

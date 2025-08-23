package br.com.drivecore.domain.machine;

import br.com.drivecore.infrastructure.persistence.machine.MachineRepository;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;

    public void createMachine(MachineEntity machine) {
        machineRepository.save(machine);
    }
}

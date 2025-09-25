package br.com.drivecore.domain.machine;

import br.com.drivecore.infrastructure.persistence.machine.MachineRepository;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;

    public MachineEntity findById(UUID id) {
        return machineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

}

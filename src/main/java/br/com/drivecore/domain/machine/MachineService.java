package br.com.drivecore.domain.machine;

import br.com.drivecore.core.specification.FilterCriteriaSpecification;
import br.com.drivecore.core.specification.model.FilterCriteria;
import br.com.drivecore.infrastructure.persistence.machine.MachineRepository;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;

    public MachineEntity findById(UUID id) {
        return machineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public Page<MachineEntity> listMachinePageableAndFiltered(Pageable pageable, List<FilterCriteria> filterCriteria) {
        return filterCriteria != null && !filterCriteria.isEmpty() ?
                machineRepository.findAll(new FilterCriteriaSpecification<>(filterCriteria), pageable) :
                machineRepository.findAll(pageable);
    }
}

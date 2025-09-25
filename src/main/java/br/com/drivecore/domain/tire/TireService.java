package br.com.drivecore.domain.tire;

import br.com.drivecore.core.specification.FilterCriteriaSpecification;
import br.com.drivecore.core.specification.model.FilterCriteria;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import br.com.drivecore.infrastructure.persistence.tire.TirePositionRepository;
import br.com.drivecore.infrastructure.persistence.tire.TireRepository;
import br.com.drivecore.infrastructure.persistence.tire.TireRetreadingRepository;
import br.com.drivecore.infrastructure.persistence.tire.entities.TireEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TirePositionEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TireRetreadingEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class TireService {

    private final TireRepository tireRepository;
    private final TirePositionRepository tirePositionRepository;
    private final TireRetreadingRepository tireRetreadingRepository;

    public void saveTire(TireEntity tireEntity) {
        tireRepository.save(tireEntity);
    }

    public TireEntity findById(UUID id) {
        return tireRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public Page<TireEntity> listEmployerPageableAndFiltered(Pageable pageable, List<FilterCriteria> filterCriteria) {
        return filterCriteria != null && !filterCriteria.isEmpty() ?
                tireRepository.findAll(new FilterCriteriaSpecification<>(filterCriteria), pageable) :
                tireRepository.findAll(pageable);
    }

    public void deleteTire(UUID id) {
        tireRepository.deleteById(id);
    }

    public Boolean existsTirePositionInUse(UUID machineId, UUID tireId, int axle, int side) {
        return tirePositionRepository.existsTirePositionInUse(machineId, tireId, axle, side);
    }


    public void saveTirePosition(TirePositionEntity tirePositionEntity) {
        tirePositionRepository.save(tirePositionEntity);
    }

    public List<TirePositionEntity> findTiresPositionsInUseByMachine(MachineEntity machineEntity) {
        return tirePositionRepository.findByMachineAndInUseOrderByAxleAscSideAsc(machineEntity, TRUE);
    }

    public TirePositionEntity findTirePositionById(UUID id) {
        return tirePositionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public void inactivateTirePosition(UUID tirePositionId) {
        TirePositionEntity tirePositionEntity = findTirePositionById(tirePositionId);

        tirePositionEntity.setInUse(FALSE);

        saveTirePosition(tirePositionEntity);
    }

    public void saveTireRetreading(TireRetreadingEntity tireRetreadingEntity) {
        tireRetreadingRepository.save(tireRetreadingEntity);
    }
}

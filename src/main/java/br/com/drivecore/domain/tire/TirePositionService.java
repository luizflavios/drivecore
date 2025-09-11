package br.com.drivecore.domain.tire;

import br.com.drivecore.infrastructure.persistence.tire.TirePositionRepository;
import br.com.drivecore.infrastructure.persistence.tire.entities.TirePositionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TirePositionService {

    private final TirePositionRepository tirePositionRepository;

    public void createTirePosition(TirePositionEntity tirePositionEntity) {
        tirePositionRepository.save(tirePositionEntity);
    }

    public Boolean checkIfTireIsAlreadyInUse(UUID tireId) {
        return tirePositionRepository.existsByTireAndInUse(tireId, true);
    }

    public List<TirePositionEntity> findAllTirePositions() {
        return tirePositionRepository.findAll();
    }
}

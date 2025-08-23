package br.com.drivecore.domain.tire;

import br.com.drivecore.infrastructure.persistence.tire.TirePositionRepository;
import br.com.drivecore.infrastructure.persistence.tire.entities.TirePositionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TirePositionService {

    private final TirePositionRepository tirePositionRepository;

    public void createTirePosition(TirePositionEntity tirePositionEntity) {
        tirePositionRepository.save(tirePositionEntity);
    }

}

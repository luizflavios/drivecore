package br.com.drivecore.domain.tire;

import br.com.drivecore.infrastructure.persistence.tire.TireRepository;
import br.com.drivecore.infrastructure.persistence.tire.entities.TireEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TireService {

    private final TireRepository tireRepository;

    public void createTire(TireEntity tireEntity) {
        tireRepository.save(tireEntity);
    }

}

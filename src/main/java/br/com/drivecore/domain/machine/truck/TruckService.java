package br.com.drivecore.domain.machine.truck;

import br.com.drivecore.infrastructure.persistence.machine.truck.TruckRepository;
import br.com.drivecore.infrastructure.persistence.machine.truck.entities.TruckEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TruckService {

    private final TruckRepository truckRepository;

    public void createTruck(TruckEntity truckEntity) {
        truckRepository.save(truckEntity);
    }

}

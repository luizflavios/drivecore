package br.com.drivecore.domain.machine.wheeling.truck;

import br.com.drivecore.domain.machine.wheeling.truck.exception.TruckNotFoundException;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.TruckRepository;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import br.com.drivecore.infrastructure.persistence.specification.FilterCriteriaSpecification;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TruckService {

    private final TruckRepository truckRepository;

    public void createTruck(TruckEntity truckEntity) {
        truckRepository.save(truckEntity);
    }

    public TruckEntity findTruckById(UUID id) {
        return truckRepository
                .findById(id)
                .orElseThrow(() -> new TruckNotFoundException(id));
    }

    public Page<TruckEntity> findAllPageableAndFilterable(Pageable pageable, List<FilterCriteria> filterCriteria) {
        if (nonNull(filterCriteria) && !filterCriteria.isEmpty()) {
            return truckRepository.findAll(new FilterCriteriaSpecification<>(filterCriteria), pageable);
        }

        return truckRepository.findAll(pageable);
    }
}

package br.com.drivecore.domain.machine.wheeling.truck;

import br.com.drivecore.core.specification.FilterCriteriaSpecification;
import br.com.drivecore.core.specification.model.FilterCriteria;
import br.com.drivecore.domain.machine.wheeling.truck.exception.TruckNotFoundException;
import br.com.drivecore.domain.machine.wheeling.truck.exception.TruckTrailerCombinationNotFoundException;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.TruckRepository;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.TruckTrailerCombinationRepository;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckTrailerCombinationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TruckService {

    private final TruckRepository truckRepository;
    private final TruckTrailerCombinationRepository truckTrailerCombinationRepository;

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

    public Boolean checkIfTrailerIsAvailableToNewCombination(TrailerEntity trailerEntity) {
        return !truckTrailerCombinationRepository.existsByTrailerAndNotFinished(trailerEntity);
    }

    public void saveTruckTrailerCombination(TruckTrailerCombinationEntity truckTrailerCombination) {
        truckTrailerCombinationRepository.save(truckTrailerCombination);
    }

    public void finishTruckTrailerCombination(UUID truckTrailerCombinationId) {
        TruckTrailerCombinationEntity truckTrailerCombination = truckTrailerCombinationRepository
                .findById(truckTrailerCombinationId)
                .orElseThrow(() -> new TruckTrailerCombinationNotFoundException(truckTrailerCombinationId));

        if (isNull(truckTrailerCombination.getFinishedAt())) {
            truckTrailerCombination.setFinishedAt(LocalDateTime.now());
        }


        saveTruckTrailerCombination(truckTrailerCombination);
    }
}

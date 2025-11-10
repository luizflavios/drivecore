package br.com.drivecore.application.machine;

import br.com.drivecore.controller.machine.model.CreateTruckTrailerCombinationRequestDTO;
import br.com.drivecore.controller.machine.model.TruckTrailerCombinationResponseDTO;
import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import br.com.drivecore.domain.machine.wheeling.TrailerService;
import br.com.drivecore.domain.machine.wheeling.TruckService;
import br.com.drivecore.domain.machine.wheeling.TruckTrailerCombinationService;
import br.com.drivecore.domain.machine.wheeling.mapper.TruckTrailerCombinationMapper;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckTrailerCombinationEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TruckTrailerCombinationApplicationService {

    private final TruckTrailerCombinationService truckTrailerCombinationService;
    private final TruckService truckService;
    private final TrailerService trailerService;

    public TruckTrailerCombinationResponseDTO createTruckTrailerCombination(CreateTruckTrailerCombinationRequestDTO createTruckTrailerCombinationRequestDTO) {
        UUID truckId = createTruckTrailerCombinationRequestDTO.getTruckId();
        UUID trailerId = createTruckTrailerCombinationRequestDTO.getTrailerId();

        truckTrailerCombinationService.checkIfTruckOrTrailerIsInUse(truckId, trailerId);

        TruckEntity truck = truckService.findById(truckId);

        TrailerEntity trailer = trailerService.findById(trailerId);

        TruckTrailerCombinationEntity truckTrailerCombination = TruckTrailerCombinationMapper.INSTANCE.toEntity(truck, trailer);

        truckTrailerCombinationService.saveTruckTrailerCombination(truckTrailerCombination);

        log.info("Created Truck-Trailer Combination {} - Truck ID {} - Trailer ID {}", truckTrailerCombination.getId(), truckId, trailerId);

        return TruckTrailerCombinationMapper.INSTANCE.toResponseDTO(truckTrailerCombination);
    }

    public TruckTrailerCombinationResponseDTO getTruckTrailerCombinationDetail(UUID truckTrailerCombinationId) {
        TruckTrailerCombinationEntity truckTrailerCombination = truckTrailerCombinationService.findById(truckTrailerCombinationId);

        return TruckTrailerCombinationMapper.INSTANCE.toResponseDTO(truckTrailerCombination);
    }

    public Page<TruckTrailerCombinationResponseDTO> getTruckTrailersCombinations(FilteredAndPageableRequestDTO filteredAndPageableRequestDTO) {
        Page<TruckTrailerCombinationEntity> truckTrailerCombinationEntities = truckTrailerCombinationService.listTruckTrailerCombinationPageableAndFiltered(
                filteredAndPageableRequestDTO.getPageRequest(),
                filteredAndPageableRequestDTO.getFilters()
        );

        return truckTrailerCombinationEntities.map(TruckTrailerCombinationMapper.INSTANCE::toResponseDTO);
    }

    public void finishTruckTrailerCombination(UUID truckTrailerCombinationId) {
        truckTrailerCombinationService.finishTruckTrailerCombination(truckTrailerCombinationId);

        log.info("Finished Truck-Trailer Combination {}", truckTrailerCombinationId);
    }

}

package br.com.drivecore.application.machine;

import br.com.drivecore.controller.machine.model.*;
import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import br.com.drivecore.domain.employer.EmployerService;
import br.com.drivecore.domain.machine.wheeling.TrailerService;
import br.com.drivecore.domain.machine.wheeling.TruckService;
import br.com.drivecore.domain.machine.wheeling.TruckTrailerCombinationService;
import br.com.drivecore.domain.machine.wheeling.mapper.TruckTrailerCombinationMapper;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckTrailerCombinationEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TruckTrailerCombinationApplicationService {

    private final TruckTrailerCombinationService truckTrailerCombinationService;
    private final TruckService truckService;
    private final TrailerService trailerService;
    private final EmployerService employerService;

    @Transactional
    public TruckTrailerCombinationResponseDTO createTruckTrailerCombination(CreateTruckTrailerCombinationRequestDTO createTruckTrailerCombinationRequestDTO) {
        UUID truckId = createTruckTrailerCombinationRequestDTO.getTruckId();
        UUID trailerId = createTruckTrailerCombinationRequestDTO.getTrailerId();
        UUID employerId = createTruckTrailerCombinationRequestDTO.getEmployerId();

        truckTrailerCombinationService.checkIfTruckOrTrailerIsInUse(truckId, trailerId);

        TruckEntity truck = truckService.findById(truckId);
        TrailerEntity trailer = trailerService.findById(trailerId);
        EmployerEntity employerEntity = employerService.findById(employerId);

        TruckTrailerCombinationEntity truckTrailerCombination =
                TruckTrailerCombinationMapper.INSTANCE.toEntity(truck,
                        trailer, employerEntity,
                        createTruckTrailerCombinationRequestDTO.getInitialMileage(),
                        createTruckTrailerCombinationRequestDTO.getCreatedAt());

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

    @Transactional
    public void updateTruckTrailerCombination(UUID truckTrailerCombinationId, UpdateTruckTrailerCombinationRequestDTO updateTruckTrailerCombinationRequestDTO) {
        TruckTrailerCombinationEntity truckTrailerCombination = truckTrailerCombinationService.findById(truckTrailerCombinationId);

        TruckTrailerCombinationMapper.INSTANCE.updateEntity(truckTrailerCombination, updateTruckTrailerCombinationRequestDTO);

        checkChangeOnPrincipalTruckTrailerIdentityInfo(updateTruckTrailerCombinationRequestDTO, truckTrailerCombination);

        truckTrailerCombinationService.saveTruckTrailerCombination(truckTrailerCombination);

        log.info("Truck-Trailer Combination {} successfully updated", truckTrailerCombinationId);
    }

    private void checkChangeOnPrincipalTruckTrailerIdentityInfo(UpdateTruckTrailerCombinationRequestDTO updateTruckTrailerCombinationRequestDTO, TruckTrailerCombinationEntity truckTrailerCombination) {
        if (!truckTrailerCombination.getTrailer().getId()
                .equals(updateTruckTrailerCombinationRequestDTO.getTrailerId())) {
            TrailerEntity trailer =
                    trailerService.findById(updateTruckTrailerCombinationRequestDTO.getTrailerId());

            truckTrailerCombination.setTrailer(trailer);
        }

        if (!truckTrailerCombination.getTruck().getId()
                .equals(updateTruckTrailerCombinationRequestDTO.getTruckId())) {
            TruckEntity truck =
                    truckService.findById(updateTruckTrailerCombinationRequestDTO.getTruckId());

            truckTrailerCombination.setTruck(truck);
        }

        if (!truckTrailerCombination.getEmployer().getId()
                .equals(updateTruckTrailerCombinationRequestDTO.getEmployerId())) {
            EmployerEntity employer =
                    employerService.findById(updateTruckTrailerCombinationRequestDTO.getEmployerId());

            truckTrailerCombination.setEmployer(employer);
        }
    }

    public void finishTruckTrailerCombination(UUID truckTrailerCombinationId, FinishTruckTrailerCombinationRequestDTO finishTruckTrailerCombinationRequestDTO) {
        truckTrailerCombinationService.finishTruckTrailerCombination(truckTrailerCombinationId, finishTruckTrailerCombinationRequestDTO.getFinalMileage(), finishTruckTrailerCombinationRequestDTO.getFinishedAt());

        log.info("Finished Truck-Trailer Combination {}", truckTrailerCombinationId);
    }

    public List<SummaryMachineResponseDTO> getAvailableTrucks() {
        List<TruckEntity> availableTrucks =
                truckService.findAvailableTrucksToCoupling();

        return availableTrucks.stream().map(TruckTrailerCombinationMapper.INSTANCE::toSummaryResponseDTO).toList();
    }

    public List<SummaryMachineResponseDTO> getAvailableTrailers() {
        List<TrailerEntity> availableTrailers =
                trailerService.findAvailableTrailersToCoupling();

        return availableTrailers.stream().map(TruckTrailerCombinationMapper.INSTANCE::toSummaryResponseDTO).toList();
    }
}

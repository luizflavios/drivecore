package br.com.drivecore.application.machine.wheeling.truck;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.application.machine.wheeling.trailer.TrailerApplicationService;
import br.com.drivecore.controller.machine.wheeling.truck.model.CreateTruckRequestDTO;
import br.com.drivecore.controller.machine.wheeling.truck.model.TruckResponseDTO;
import br.com.drivecore.controller.machine.wheeling.truck.model.TruckTrailerCombinationRequestDTO;
import br.com.drivecore.controller.machine.wheeling.truck.model.TruckTrailerCombinationResponseDTO;
import br.com.drivecore.core.specification.model.FilterCriteria;
import br.com.drivecore.domain.machine.wheeling.truck.TruckService;
import br.com.drivecore.domain.machine.wheeling.truck.mapper.TruckMapper;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckTrailerCombinationEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.lang.Boolean.TRUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class TruckApplicationService {

    private final EmployerApplicationService employerApplicationService;
    private final TruckService truckService;
    private final TrailerApplicationService trailerApplicationService;


    @Transactional
    public TruckResponseDTO createTruck(CreateTruckRequestDTO createTruckRequestDTO) {
        EmployerEntity employerEntity = employerApplicationService.getLoggedEmployer();

        TruckEntity truckEntity = TruckMapper.INSTANCE.toEntity(createTruckRequestDTO, employerEntity);

        truckService.createTruck(truckEntity);

        log.info("Truck {} successfully created by - {}", truckEntity.getId(), truckEntity.getCreatedBy().getId());

        return TruckMapper.INSTANCE.toTruckResponseDTO(truckEntity);
    }

    public TruckEntity findDomainTruckById(UUID id) {
        return truckService.findTruckById(id);
    }

    public Page<TruckResponseDTO> listTrucksPageable(int page, int size, List<FilterCriteria> filterCriteria) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TruckEntity> truckEntityPage = truckService.findAllPageableAndFilterable(pageable, filterCriteria);

        return truckEntityPage.map(TruckMapper.INSTANCE::toTruckResponseDTO);
    }

    public TruckTrailerCombinationResponseDTO createTruckTrailerCombination(TruckTrailerCombinationRequestDTO truckTrailerCombinationRequestDTO) {
        TrailerEntity trailerEntity = trailerApplicationService
                .findDomainTrailerById(truckTrailerCombinationRequestDTO.getTrailerId());

        TruckEntity truckEntity = truckService.findTruckById(truckTrailerCombinationRequestDTO.getTruckId());

        Boolean isPossibleCreateNewCombination = truckService.checkIfTrailerIsAvailableToNewCombination(trailerEntity);

        TruckTrailerCombinationResponseDTO truckTrailerCombinationResponseDTO;

        if (TRUE.equals(isPossibleCreateNewCombination)) {
            TruckTrailerCombinationEntity truckTrailerCombination = TruckMapper.INSTANCE
                    .toTruckTrailerCombinationEntity(truckEntity, trailerEntity);

            truckService.saveTruckTrailerCombination(truckTrailerCombination);

            log.info("Truck Trailer successfully created - {}", truckTrailerCombination.getId());

            return TruckMapper.INSTANCE.toTruckTrailerCombinationResponseDTO(truckTrailerCombination);
        } else {
            log.info("Truck Trailer combination can´t be created because trailer {} is already in use", trailerEntity.getId());

            truckTrailerCombinationResponseDTO = new TruckTrailerCombinationResponseDTO(isPossibleCreateNewCombination);
        }

        return truckTrailerCombinationResponseDTO;
    }

    public void finishTruckTrailerCombination(UUID truckTrailerCombinationId) {
        truckService.finishTruckTrailerCombination(truckTrailerCombinationId);

        log.info("Truck trailer combination successfully finished - {}", truckTrailerCombinationId);
    }
}

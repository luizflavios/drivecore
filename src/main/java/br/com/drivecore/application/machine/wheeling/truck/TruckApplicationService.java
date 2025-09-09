package br.com.drivecore.application.machine.wheeling.truck;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.controller.machine.wheeling.truck.model.CreateTruckRequestDTO;
import br.com.drivecore.controller.machine.wheeling.truck.model.TruckResponseDTO;
import br.com.drivecore.domain.machine.wheeling.truck.TruckService;
import br.com.drivecore.domain.machine.wheeling.truck.mapper.TruckMapper;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TruckApplicationService {

    private final EmployerApplicationService employerApplicationService;

    private final TruckService truckService;

    @Transactional
    public TruckResponseDTO createTruck(CreateTruckRequestDTO createTruckRequestDTO) {
        EmployerEntity employerEntity = employerApplicationService.getLoggedEmployer();

        TruckEntity truckEntity = TruckMapper.INSTANCE.toEntity(createTruckRequestDTO, employerEntity);

        truckService.createTruck(truckEntity);

        log.info("Truck {} successfully create by - {}", truckEntity.getId(), truckEntity.getCreatedBy().getId());

        return TruckMapper.INSTANCE.toTruckResponseDTO(truckEntity);
    }

    public Page<TruckResponseDTO> listTrucksPageable(int page, int size, List<FilterCriteria> filterCriteria) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TruckEntity> truckEntityPage = truckService.findAllPageableAndFilterable(pageable, filterCriteria);

        return truckEntityPage.map(TruckMapper.INSTANCE::toTruckResponseDTO);
    }

    public void createTruckTrailerCombination(UUID truckId, UUID trailerId) {
        
    }
}

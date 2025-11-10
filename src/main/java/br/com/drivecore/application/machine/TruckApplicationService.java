package br.com.drivecore.application.machine;

import br.com.drivecore.controller.machine.model.CreateTruckRequestDTO;
import br.com.drivecore.controller.machine.model.TruckResponseDTO;
import br.com.drivecore.controller.machine.model.UpdateTruckRequestDTO;
import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import br.com.drivecore.domain.machine.wheeling.TruckService;
import br.com.drivecore.domain.machine.wheeling.mapper.TruckMapper;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TruckApplicationService {

    private final TruckService truckService;

    public TruckResponseDTO createTruck(CreateTruckRequestDTO createTruckRequestDTO) {
        TruckEntity truckEntity = TruckMapper.INSTANCE.toEntity(createTruckRequestDTO);

        truckService.saveTruck(truckEntity);

        log.info("Truck {} successfully created", truckEntity.getId());

        return TruckMapper.INSTANCE.toResponseDTO(truckEntity);
    }

    public TruckResponseDTO getTruckDetail(UUID id) {
        TruckEntity truckEntity = truckService.findById(id);

        return TruckMapper.INSTANCE.toResponseDTO(truckEntity);
    }

    public Page<TruckResponseDTO> getTrucks(FilteredAndPageableRequestDTO filteredAndPageableRequestDTO) {
        Page<TruckEntity> employerEntityPage = truckService.listTruckPageableAndFiltered(
                filteredAndPageableRequestDTO.getPageRequest(),
                filteredAndPageableRequestDTO.getFilters()
        );

        return employerEntityPage.map(TruckMapper.INSTANCE::toResponseDTO);
    }

    public void updateTruck(UUID id, @Valid UpdateTruckRequestDTO updateTruckRequestDTO) {
        TruckEntity truckEntity = truckService.findById(id);

        TruckMapper.INSTANCE.updateEntity(truckEntity, updateTruckRequestDTO);

        truckService.saveTruck(truckEntity);

        log.info("Truck {} successfully updated", id);
    }

    public void deleteTruck(UUID id) {
        truckService.deleteTruck(id);

        log.info("Truck {} successfully deleted", id);
    }
}

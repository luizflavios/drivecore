package br.com.drivecore.application.tire;

import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import br.com.drivecore.controller.tire.model.*;
import br.com.drivecore.domain.machine.MachineService;
import br.com.drivecore.domain.tire.TireService;
import br.com.drivecore.domain.tire.exception.AlreadyExistsTirePositionException;
import br.com.drivecore.domain.tire.mapper.TireMapper;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TireEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TirePositionEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TireRetreadingEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static java.lang.Boolean.TRUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class TireApplicationService {

    private final TireService tireService;
    private final MachineService machineService;

    public TireResponseDTO createTire(CreateTireRequestDTO createTireRequestDTO) {
        TireEntity tireEntity = TireMapper.INSTANCE.toEntity(createTireRequestDTO);

        tireService.saveTire(tireEntity);

        log.info("Tire {} successfully created", tireEntity.getId());

        return TireMapper.INSTANCE.toResponseDTO(tireEntity);
    }

    public TireResponseDTO getTireDetail(UUID id) {
        TireEntity tireEntity = tireService.findById(id);

        TireResponseDTO tireResponseDTO = TireMapper.INSTANCE.toResponseDTO(tireEntity);

        addRetreadingInfo(tireEntity, tireResponseDTO);

        return tireResponseDTO;
    }

    private void addRetreadingInfo(TireEntity tireEntity, TireResponseDTO tireResponseDTO) {
        if (!tireEntity.getRetreads().isEmpty()) {
            tireEntity.getRetreads()
                    .stream()
                    .max(Comparator.comparing(TireRetreadingEntity::getCreatedAt))
                    .map(TireRetreadingEntity::getMileage)
                    .ifPresent(tireResponseDTO::setTotalMileage);

            tireResponseDTO.setTotalMileage(tireEntity.getMileage() + tireResponseDTO.getTotalMileage());

            tireResponseDTO.setRetreadingCount(tireEntity.getRetreads().size());
        }
    }

    public Page<TireResponseDTO> getTires(FilteredAndPageableRequestDTO filteredAndPageableRequestDTO) {
        Page<TireEntity> employerEntityPage = tireService.listEmployerPageableAndFiltered(
                filteredAndPageableRequestDTO.getPageRequest(),
                filteredAndPageableRequestDTO.getFilters()
        );

        return employerEntityPage.map(TireMapper.INSTANCE::toResponseDTO);
    }

    public void updateTire(UUID id, UpdateTireRequestDTO updateTireRequestDTO) {
        TireEntity tireEntity = tireService.findById(id);

        TireMapper.INSTANCE.updateEntity(tireEntity, updateTireRequestDTO);

        tireService.saveTire(tireEntity);

        log.info("Tire {} successfully updated", id);
    }

    public void deleteTire(UUID id) {
        tireService.deleteTire(id);

        log.info("Tire {} successfully deleted", id);
    }

    public TirePositionResponseDTO addTirePositionToMachine(CreateTirePositionRequestDTO createTirePositionRequestDTO) {
        checkIfExistsTirePositionInUse(
                createTirePositionRequestDTO.getMachineId(),
                createTirePositionRequestDTO.getTireId(),
                createTirePositionRequestDTO.getAxle(),
                createTirePositionRequestDTO.getSide()
        );

        TireEntity tireEntity = tireService.findById(createTirePositionRequestDTO.getTireId());

        MachineEntity machineEntity = machineService.findById(createTirePositionRequestDTO.getMachineId());

        TirePositionEntity tirePositionEntity = TireMapper.INSTANCE.toTirePositionEntity(
                tireEntity, machineEntity, createTirePositionRequestDTO.getAxle(), createTirePositionRequestDTO.getSide()
        );

        tireService.saveTirePosition(tirePositionEntity);

        log.info("Tire Position {} successfully created", tirePositionEntity.getId());

        return TireMapper.INSTANCE.toTirePositionEntityResponseDTO(tirePositionEntity);
    }

    private void checkIfExistsTirePositionInUse(UUID machineId, UUID tireId, int axle, int side) {
        var alreadyExistsTirePositionWithSameParameters = tireService.existsTirePositionInUse(
                machineId,
                tireId,
                axle,
                side
        );

        if (TRUE.equals(alreadyExistsTirePositionWithSameParameters)) {
            var errorMessage = String.format("Tire position already exists in use for machineId: %s, tireId: %s, axle: %d, side: %d",
                    machineId,
                    tireId,
                    axle,
                    side);

            log.error(errorMessage);

            throw new AlreadyExistsTirePositionException(errorMessage);
        }
    }

    public TirePositionByMachineResponseDTO getTiresPositionsInUseByMachine(UUID machineId) {
        MachineEntity machineEntity = machineService.findById(machineId);

        List<TirePositionEntity> tirePositionEntityList = tireService.findTiresPositionsInUseByMachine(machineEntity);

        return TireMapper.INSTANCE.toTirePositionByMachineResponseDTO(machineEntity, tirePositionEntityList);
    }

    public void inactivateTirePosition(UUID tirePositionId) {
        tireService.inactivateTirePosition(tirePositionId);

        log.info("Tire Position {} successfully inactivated", tirePositionId);
    }

    @Transactional
    public void addRetreadingToTire(UUID tireId) {
        TireEntity tireEntity = tireService.findById(tireId);

        TireRetreadingEntity tireRetreadingEntity = TireMapper.INSTANCE.toTireRetreadingEntity(tireEntity);

        tireService.saveTireRetreading(tireRetreadingEntity);

        tireEntity.setMileage(0L);

        tireService.saveTire(tireEntity);

        log.info("Retreading {} successfully added to tire {}", tireRetreadingEntity.getId(), tireId);
    }

}

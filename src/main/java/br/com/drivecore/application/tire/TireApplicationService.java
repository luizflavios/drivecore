package br.com.drivecore.application.tire;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.controller.tire.model.CreateTirePositionDTO;
import br.com.drivecore.controller.tire.model.CreateTireRequestDTO;
import br.com.drivecore.controller.tire.model.TirePositionResponseDTO;
import br.com.drivecore.controller.tire.model.TireResponseDTO;
import br.com.drivecore.domain.tire.TirePositionService;
import br.com.drivecore.domain.tire.TireService;
import br.com.drivecore.domain.tire.mapper.TireMapper;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TireEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TirePositionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TireApplicationService {

    private final EmployerApplicationService employerApplicationService;

    private final TireService tireService;
    private final TirePositionService tirePositionService;

    public TireResponseDTO createTire(CreateTireRequestDTO createTireRequestDTO) {
        EmployerEntity createdBy = employerApplicationService.getLoggedEmployer();

        TireEntity tireEntity = TireMapper.INSTANCE.toTireEntity(createTireRequestDTO, createdBy);

        tireService.createTire(tireEntity);

        log.info("Tire {} successfully created by - {}", tireEntity.getId(), createdBy.getId());

        return TireMapper.INSTANCE.toTireResponseDTO(tireEntity);
    }

    public TirePositionResponseDTO createTirePosition(CreateTirePositionDTO createTirePositionDTO) {
        EmployerEntity createdBy = employerApplicationService.getLoggedEmployer();

        TirePositionEntity tirePositionEntity = TireMapper.INSTANCE.toTirePositionEntity(createTirePositionDTO, createdBy);

        tirePositionService.createTirePosition(tirePositionEntity);

        log.info("Tire position {} successfully created by - {}", tirePositionEntity.getId(), createdBy.getId());

        return TireMapper.INSTANCE.toTirePositionResponseDTO(tirePositionEntity);
    }

}

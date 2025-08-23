package br.com.drivecore.application.employer;

import br.com.drivecore.application.authentication.AuthenticationApplicationService;
import br.com.drivecore.controller.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.controller.employer.model.EmployerResponseDTO;
import br.com.drivecore.domain.employer.EmployerService;
import br.com.drivecore.domain.employer.mapper.EmployerMapper;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployerApplicationService {

    private final AuthenticationApplicationService authenticationApplicationService;

    private final EmployerService employerService;

    @Transactional
    public EmployerResponseDTO createEmployer(CreateEmployerRequestDTO createEmployerRequestDTO) {
        UserEntity userEntity = authenticationApplicationService.createDomainUser(createEmployerRequestDTO.getUser());

        EmployerEntity employerEntity = EmployerMapper.INSTANCE.toEntity(createEmployerRequestDTO, userEntity);

        employerService.createEmployer(employerEntity);

        log.info("Employer successfully created - {}", employerEntity.getId());

        return EmployerMapper.INSTANCE.toEmployerResponseDTO(employerEntity);
    }

    public EmployerEntity getLoggedEmployer() {
        UUID userId = authenticationApplicationService.getLoggedUserId();

        return employerService.findByUserId(userId);
    }

}

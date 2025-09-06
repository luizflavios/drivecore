package br.com.drivecore.application.employer;

import br.com.drivecore.application.authentication.AuthenticationApplicationService;
import br.com.drivecore.controller.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.controller.employer.model.EmployerResponseDTO;
import br.com.drivecore.domain.employer.EmployerService;
import br.com.drivecore.domain.employer.mapper.EmployerMapper;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import jakarta.validation.Valid;
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

    public Page<EmployerResponseDTO> listEmployersPageable(int page, int size, @Valid List<FilterCriteria> filterCriteria) {
        Pageable pageable = PageRequest.of(page, size);

        Page<EmployerEntity> employerEntityPage = employerService
                .findEmployersPageableAndFilterable(pageable, filterCriteria);

        log.info("Employers successfully list");

        return employerEntityPage.map(EmployerMapper.INSTANCE::toEmployerResponseDTO);
    }
}

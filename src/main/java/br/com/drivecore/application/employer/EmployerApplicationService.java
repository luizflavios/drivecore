package br.com.drivecore.application.employer;

import br.com.drivecore.application.user.UserApplicationService;
import br.com.drivecore.core.generics.domain.mapper.GenericMapper;
import br.com.drivecore.core.generics.domain.model.IdReferenceGenericDTO;
import br.com.drivecore.domain.employer.EmployerService;
import br.com.drivecore.domain.employer.mapper.EmployerMapper;
import br.com.drivecore.domain.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.domain.employer.model.EmployerRequestDTO;
import br.com.drivecore.domain.employer.model.EmployerResponseDTO;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployerApplicationService {

    private final EmployerService employerService;
    private final UserApplicationService userApplicationService;

    public IdReferenceGenericDTO createEmployer(@NotNull CreateEmployerRequestDTO createEmployerRequestDTO) {
        var user = createUserToEmployer(createEmployerRequestDTO);

        var employer = employerService.createEmployer(createEmployerRequestDTO, user);

        log.info("Successfully employer created - {}", employer.getId());

        return GenericMapper.INSTANCE.toIdReferenceGenericDTO(employer);
    }

    private UserEntity createUserToEmployer(CreateEmployerRequestDTO createEmployerRequestDTO) {
        var createUserRequestDTO = EmployerMapper
                .INSTANCE.toCreateUserRequestDTO(createEmployerRequestDTO);

        return userApplicationService.createUserEntity(createUserRequestDTO);
    }

    public EmployerResponseDTO getEmployerById(@NotNull UUID id) {
        var employerEntity = getEmployer(id);

        return EmployerMapper.INSTANCE.toEmployerResponseDTO(employerEntity);
    }

    public EmployerEntity getEmployer(UUID id) {
        return employerService.findEmployerById(id);
    }


    public List<EmployerResponseDTO> getEmployersFiltered(@Nullable List<FilterCriteria> filters) {
        List<EmployerEntity> employers;

        if (nonNull(filters) && !filters.isEmpty()) {
            employers = employerService.findByCriteriaFilters(filters);
        } else {
            employers = employerService.findAll();
        }

        return employers.stream()
                .map(EmployerMapper.INSTANCE::toEmployerResponseDTO)
                .toList();
    }

    public EmployerResponseDTO updateEmployer(@NotNull UUID id, @NotNull EmployerRequestDTO employerRequestDTO) {
        var employer = employerService.updateEmployer(employerRequestDTO, id);

        log.info("Successfully employer updated - {}", employer.getId());

        return EmployerMapper.INSTANCE.toEmployerResponseDTO(employer);
    }
}

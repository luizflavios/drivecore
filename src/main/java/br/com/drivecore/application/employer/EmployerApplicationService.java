package br.com.drivecore.application.employer;

import br.com.drivecore.application.authentication.AuthenticationApplicationService;
import br.com.drivecore.controller.authentication.model.CreateUserRequestDTO;
import br.com.drivecore.controller.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.controller.employer.model.EmployerResponseDTO;
import br.com.drivecore.controller.employer.model.UpdateEmployerRequestDTO;
import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import br.com.drivecore.domain.authentication.enums.UserStatus;
import br.com.drivecore.domain.employer.EmployerService;
import br.com.drivecore.domain.employer.mapper.EmployerMapper;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployerApplicationService {

    private final EmployerService employerService;
    private final AuthenticationApplicationService authenticationApplicationService;

    @Transactional
    public EmployerResponseDTO createEmployer(CreateEmployerRequestDTO requestDTO) {
        UserEntity userEntity = authenticationApplicationService
                .createUserEntity(new CreateUserRequestDTO(requestDTO.getSocialNumber(), requestDTO.getEmail()));

        EmployerEntity employerEntity = EmployerMapper.INSTANCE.toEntity(requestDTO, userEntity);

        employerService.saveEmployer(employerEntity);

        log.info("Employer {} successfully created", employerEntity.getId());

        return EmployerMapper.INSTANCE.toResponseDTO(employerEntity);
    }

    public EmployerResponseDTO getEmployerDetail(UUID employerId) {
        EmployerEntity employerEntity = employerService.findById(employerId);

        return EmployerMapper.INSTANCE.toResponseDTO(employerEntity);
    }

    public void updateEmployer(UUID id, UpdateEmployerRequestDTO updateEmployerRequestDTO) {
        EmployerEntity employerEntity = employerService.findById(id);

        EmployerMapper.INSTANCE.updateEntity(employerEntity, updateEmployerRequestDTO);

        if (isNotBlank(updateEmployerRequestDTO.getSocialNumber()) && employerEntity.getSocialNumber()
                .compareTo(updateEmployerRequestDTO.getSocialNumber()) != 0) {
            String socialNumber = updateEmployerRequestDTO.getSocialNumber();

            employerEntity.setSocialNumber(socialNumber);
            employerEntity.getUser().setUsername(socialNumber);
        }

        if (employerEntity.getUser().getStatus().isActive() != updateEmployerRequestDTO.isActive()) {
            employerEntity.getUser().setStatus(updateEmployerRequestDTO.isActive() ? UserStatus.ACTIVE : UserStatus.INACTIVE);
        }

        employerService.saveEmployer(employerEntity);
    }

    public void deleteEmployer(UUID id) {
        employerService.deleteEmployer(id);

        log.info("Employer {} successfully deleted", id);
    }

    public Page<EmployerResponseDTO> getEmployers(FilteredAndPageableRequestDTO filteredAndPageableRequestDTO) {
        Page<EmployerEntity> employerEntityPage = employerService.listEmployerPageableAndFiltered(
                filteredAndPageableRequestDTO.getPageRequest(),
                filteredAndPageableRequestDTO.getFilters()
        );

        return employerEntityPage.map(EmployerMapper.INSTANCE::toResponseDTO);
    }

}

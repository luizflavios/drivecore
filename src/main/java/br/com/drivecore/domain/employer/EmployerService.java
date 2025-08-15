package br.com.drivecore.domain.employer;

import br.com.drivecore.domain.employer.exception.EmployerNotFoundException;
import br.com.drivecore.domain.employer.mapper.EmployerMapper;
import br.com.drivecore.domain.employer.model.CreateEmployerRequestDTO;
import br.com.drivecore.domain.employer.model.EmployerRequestDTO;
import br.com.drivecore.infrastructure.persistence.employer.EmployerRepository;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.specification.GenericSpecification;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployerService {

    private final EmployerRepository employerRepository;

    public EmployerEntity createEmployer(CreateEmployerRequestDTO employerRequestDTO, UserEntity user) {
        var employerEntity = EmployerMapper.INSTANCE.toEntity(employerRequestDTO, user);

        return save(employerEntity);
    }

    private EmployerEntity save(EmployerEntity employerEntity) {
        return employerRepository.save(employerEntity);
    }

    public EmployerEntity findEmployerById(UUID id) {
        return employerRepository
                .findById(id)
                .orElseThrow(() -> new EmployerNotFoundException(id));
    }

    public List<EmployerEntity> findAll() {
        return employerRepository.findAll();
    }

    public List<EmployerEntity> findByCriteriaFilters(List<FilterCriteria> filters) {
        var specification = new GenericSpecification<EmployerEntity>(filters);

        return employerRepository.findAll(specification);
    }

    public EmployerEntity updateEmployer(EmployerRequestDTO requestDTO, UUID id) {
        var employer = findEmployerById(id);

        EmployerMapper.INSTANCE.copyProperties(requestDTO, employer);

        return save(employer);
    }
}

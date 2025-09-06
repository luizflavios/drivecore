package br.com.drivecore.domain.employer;

import br.com.drivecore.domain.employer.exception.EmployerNotFoundException;
import br.com.drivecore.domain.employer.exception.EmployerNotLocatedByUsernameException;
import br.com.drivecore.infrastructure.persistence.employer.EmployerRepository;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.specification.FilterCriteriaSpecification;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class EmployerService {

    private final EmployerRepository employerRepository;

    public void saveEmployer(EmployerEntity employer) {
        employerRepository.save(employer);
    }

    public EmployerEntity findByUserId(UUID userId) {
        return employerRepository
                .findByUserId(userId)
                .orElseThrow(() -> new EmployerNotLocatedByUsernameException(userId));
    }

    public Page<EmployerEntity> findEmployersPageableAndFilterable(Pageable pageable, List<FilterCriteria> filterCriteria) {
        if (nonNull(filterCriteria) && !filterCriteria.isEmpty()) {
            return employerRepository.findAll(new FilterCriteriaSpecification<>(filterCriteria), pageable);
        }

        return employerRepository.findAll(pageable);
    }

    public EmployerEntity findById(UUID id) {
        return employerRepository.findById(id).orElseThrow(() -> new EmployerNotFoundException(id));
    }

}

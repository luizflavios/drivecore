package br.com.drivecore.domain.employer;

import br.com.drivecore.controller.employer.model.SummaryEmployerResponseDTO;
import br.com.drivecore.core.specification.FilterCriteriaSpecification;
import br.com.drivecore.core.specification.model.FilterCriteria;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import br.com.drivecore.infrastructure.persistence.employer.EmployerRepository;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployerService {

    private final EmployerRepository employerRepository;

    public void saveEmployer(EmployerEntity employerEntity) {
        employerRepository.save(employerEntity);
    }

    public EmployerEntity findById(UUID employerId) {
        return employerRepository.findById(employerId).orElseThrow(() -> new EntityNotFoundException(employerId.toString()));
    }

    public Page<EmployerEntity> listEmployerPageableAndFiltered(Pageable pageable, List<FilterCriteria> filterCriteria) {
        return filterCriteria != null && !filterCriteria.isEmpty() ?
                employerRepository.findAll(new FilterCriteriaSpecification<>(filterCriteria), pageable) :
                employerRepository.findAll(pageable);
    }

    public long countActiveEmployers() {
        return employerRepository.countByUserStatusActive();
    }

    public EmployerEntity findByUser(UserEntity user) {
        return employerRepository.findByUser(user).orElseThrow(() -> new EntityNotFoundException(user.getId().toString()));
    }

    public void deleteEmployer(UUID id) {
        employerRepository.deleteById(id);
    }

    public List<SummaryEmployerResponseDTO> getSummaryEmployer() {
        return employerRepository.findAllSummary();
    }
}

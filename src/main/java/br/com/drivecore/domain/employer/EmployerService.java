package br.com.drivecore.domain.employer;

import br.com.drivecore.domain.employer.exception.EmployerNotLocatedByUsernameException;
import br.com.drivecore.infrastructure.persistence.employer.EmployerRepository;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployerService {

    private final EmployerRepository employerRepository;

    public void createEmployer(EmployerEntity employer) {
        employerRepository.save(employer);
    }

    public EmployerEntity findByUserId(UUID userId) {
        return employerRepository
                .findByUserId(userId)
                .orElseThrow(() -> new EmployerNotLocatedByUsernameException(userId));
    }

}

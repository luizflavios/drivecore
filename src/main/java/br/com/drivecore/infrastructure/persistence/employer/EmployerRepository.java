package br.com.drivecore.infrastructure.persistence.employer;

import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployerRepository extends JpaRepository<EmployerEntity, UUID>, JpaSpecificationExecutor<EmployerEntity> {
}

package br.com.drivecore.infrastructure.persistence.employer;

import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployerRepository extends JpaRepository<EmployerEntity, UUID>, JpaSpecificationExecutor<EmployerEntity> {

    @Query("select e from EmployerEntity e where e.user.id = :userId")
    Optional<EmployerEntity> findByUserId(UUID userId);

}

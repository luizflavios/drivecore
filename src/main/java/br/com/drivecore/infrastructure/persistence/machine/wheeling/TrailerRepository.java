package br.com.drivecore.infrastructure.persistence.machine.wheeling;

import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrailerRepository extends JpaRepository<TrailerEntity, UUID>, JpaSpecificationExecutor<TrailerEntity> {
}

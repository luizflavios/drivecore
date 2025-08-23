package br.com.drivecore.infrastructure.persistence.tire;

import br.com.drivecore.infrastructure.persistence.tire.entities.TirePositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TirePositionRepository extends JpaRepository<TirePositionEntity, UUID>, JpaSpecificationExecutor<TirePositionEntity> {
}

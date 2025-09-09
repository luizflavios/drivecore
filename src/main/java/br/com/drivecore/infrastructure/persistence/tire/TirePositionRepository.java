package br.com.drivecore.infrastructure.persistence.tire;

import br.com.drivecore.infrastructure.persistence.tire.entities.TirePositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TirePositionRepository extends JpaRepository<TirePositionEntity, UUID>, JpaSpecificationExecutor<TirePositionEntity> {
    @Query("select case when count(tp)> 0 then true else false end from TirePositionEntity tp where tp.tire.id = :tireId and tp.inUse = :inUse")
    Boolean existsByTireAndInUse(UUID tireId, Boolean inUse);
}

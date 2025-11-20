package br.com.drivecore.infrastructure.persistence.machine.wheeling;

import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TruckRepository extends JpaRepository<TruckEntity, UUID>, JpaSpecificationExecutor<TruckEntity> {

    @Query("""
            SELECT t FROM TruckEntity t
            WHERE t.id NOT IN (
                SELECT c.truck.id FROM TruckTrailerCombinationEntity c
                WHERE c.finishedAt IS NULL
            )
            """)
    List<TruckEntity> findAvailableTrucksToCoupling();
}

package br.com.drivecore.infrastructure.persistence.tire;

import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import br.com.drivecore.infrastructure.persistence.tire.entities.TirePositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TirePositionRepository extends JpaRepository<TirePositionEntity, UUID>, JpaSpecificationExecutor<TirePositionEntity> {

    @Query("""
                SELECT CASE WHEN COUNT(tp) > 0 THEN TRUE ELSE FALSE END
                FROM TirePositionEntity tp
                WHERE tp.inUse = true
                  AND tp.machine.id = :machineId
                  AND tp.tire.id = :tireId
                  AND tp.axle = :axle
                  AND tp.side = :side
            """)
    Boolean existsTirePositionInUse(UUID machineId, UUID tireId, int axle, int side);

    List<TirePositionEntity> findByMachineAndInUseOrderByAxleAscSideAsc(MachineEntity machineEntity, Boolean aTrue);
}

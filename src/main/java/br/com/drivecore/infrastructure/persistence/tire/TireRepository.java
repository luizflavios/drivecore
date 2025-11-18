package br.com.drivecore.infrastructure.persistence.tire;

import br.com.drivecore.controller.tire.model.SummaryTireResponseDTO;
import br.com.drivecore.infrastructure.persistence.tire.entities.TireEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TireRepository extends JpaRepository<TireEntity, UUID>, JpaSpecificationExecutor<TireEntity> {

    @Query(value = """
            SELECT new br.com.drivecore.controller.tire.model.SummaryTireResponseDTO(
                t.id,
                t.fireCode,
                t.manufacturer,
                t.manufactureYear,
                t.mileage,
                t.tireCondition,
                t.tireStatus,
                case when m is not null then m.licensePlate end
            )
            FROM TireEntity t
            LEFT JOIN TirePositionEntity tp on tp.tire = t
            LEFT JOIN MachineEntity m on tp.machine = m
            """)
    Page<SummaryTireResponseDTO> getSummaryTires(Pageable pageable);

}

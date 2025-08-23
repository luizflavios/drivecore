package br.com.drivecore.infrastructure.persistence.machine.truck;

import br.com.drivecore.infrastructure.persistence.machine.truck.entities.TruckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TruckRepository extends JpaRepository<TruckEntity, UUID>, JpaSpecificationExecutor<TruckEntity> {
}

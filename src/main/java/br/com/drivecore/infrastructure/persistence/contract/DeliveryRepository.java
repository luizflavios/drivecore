package br.com.drivecore.infrastructure.persistence.contract;

import br.com.drivecore.infrastructure.persistence.contract.entities.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, UUID>, JpaSpecificationExecutor<DeliveryEntity> {

    @Query("select count(d) from DeliveryEntity d where d.finalDate is null")
    long countByFinalDateNull();

    @Query("select coalesce(max(c.referenceNumber), 0) from ContractEntity c")
    long getMaxReferenceNumber();

}

package br.com.drivecore.infrastructure.persistence.machine.wheeling;

import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckTrailerCombinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TruckTrailerCombinationRepository extends JpaRepository<TruckTrailerCombinationEntity, UUID>, JpaSpecificationExecutor<TruckTrailerCombinationEntity> {

    @Query("select case when count(tt) > 0 then true else false end from TruckTrailerCombinationEntity tt where tt.trailer = :trailer and finishedAt is null")
    Boolean existsByTrailerAndNotFinished(TrailerEntity trailer);

}

package br.com.drivecore.infrastructure.persistence.authentication;

import br.com.drivecore.infrastructure.persistence.authentication.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
}

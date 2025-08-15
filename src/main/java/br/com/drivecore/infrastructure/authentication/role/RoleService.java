package br.com.drivecore.infrastructure.authentication.role;

import br.com.drivecore.infrastructure.persistence.authentication.RoleRepository;
import br.com.drivecore.infrastructure.persistence.authentication.entities.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Set<RoleEntity> fetchRolesByIds(List<UUID> ids) {
        return new HashSet<>(roleRepository.findAllById(ids));
    }

}

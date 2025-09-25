package br.com.drivecore.domain.authentication;

import br.com.drivecore.infrastructure.persistence.authentication.RoleRepository;
import br.com.drivecore.infrastructure.persistence.authentication.entities.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<RoleEntity> findAllRoles() {
        return roleRepository.findAll();
    }

    public List<RoleEntity> findBasicRoles() {
        return roleRepository.findBasicRoles();
    }
}

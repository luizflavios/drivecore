package br.com.drivecore.infrastructure.persistence.authentication.entities;

import br.com.drivecore.core.generics.GenericEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends GenericEntity implements GrantedAuthority {

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}

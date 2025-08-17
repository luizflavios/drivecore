package br.com.drivecore.infrastructure.persistence.user.entities;

import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import br.com.drivecore.domain.user.enums.UserStatus;
import br.com.drivecore.infrastructure.persistence.authentication.entities.RoleEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

import static br.com.drivecore.domain.user.enums.UserStatus.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", table = "users"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "roles"))
    private Set<RoleEntity> roles;

    @Version
    private Long version;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return getStatus() != EXPIRED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return getStatus() != LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getStatus() != CONFIGURATION;
    }

    @Override
    public boolean isEnabled() {
        return getStatus() != INACTIVE;
    }

}

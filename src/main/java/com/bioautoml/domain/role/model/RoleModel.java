package com.bioautoml.domain.role.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.role.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel implements BaseEntity, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role roleName;

    @Override
    public String getAuthority() {
        return this.getRoleName().name();
    }
}

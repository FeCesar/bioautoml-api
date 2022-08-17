package com.bioautoml.domain.user.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.role.dto.RoleDTO;
import com.bioautoml.domain.role.model.RoleModel;
import com.bioautoml.domain.user.dto.UserDTO;
import com.bioautoml.domain.user.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserModel implements BaseEntity, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 120)
    private String fullName;

    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type = Type.DEFAULT;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleModel> roles = new ArrayList<>();

    @OneToMany(mappedBy = "userModel")
    private List<ProcessModel> processes = new ArrayList<>();

    public UserDTO toDTO(){

        List<RoleDTO> roleDTOS = this.getRoles()
                .stream()
                .map(RoleModel::toDTO)
                .collect(Collectors.toList());

        return UserDTO.builder()
                .id(this.getId())
                .fullName(this.getFullName())
                .email(this.getEmail())
                .type(this.getType())
                .roles(roleDTOS)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

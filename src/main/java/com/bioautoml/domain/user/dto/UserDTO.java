package com.bioautoml.domain.user.dto;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.role.dto.RoleDTO;
import com.bioautoml.domain.role.enums.Role;
import com.bioautoml.domain.role.model.RoleModel;
import com.bioautoml.domain.user.enums.Type;
import com.bioautoml.domain.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements BaseEntity {

    private UUID id;
    private String fullName;
    private String email;
    private Type type;
    private List<RoleDTO> roles;

    public UserModel toModel(){

        List<RoleModel> roleModels = this.getRoles()
                .stream()
                .map(RoleDTO::toModel)
                .collect(Collectors.toList());

        return UserModel.builder()
                .id(this.getId())
                .fullName(this.getFullName())
                .email(this.getEmail())
                .type(this.getType())
                .roles(roleModels)
                .build();
    }

}

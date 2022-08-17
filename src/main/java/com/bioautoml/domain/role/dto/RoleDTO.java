package com.bioautoml.domain.role.dto;

import com.bioautoml.domain.role.enums.Role;
import com.bioautoml.domain.role.model.RoleModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private UUID id;
    private String name;

    public RoleModel toModel(){
        return RoleModel.builder()
                .id(this.getId())
                .roleName(Role.valueOf(this.getName()))
                .build();
    }

}

package com.bioautoml.domain.user.dto;

import com.bioautoml.domain.role.dto.RoleDTO;
import com.bioautoml.domain.role.model.RoleModel;
import com.bioautoml.domain.user.enums.Type;
import com.bioautoml.domain.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProcessDTO {

    private UUID id;
    private String fullName;
    private String email;
    private Type type;

    public UserModel toModel(){

        return UserModel.builder()
                .id(this.getId())
                .fullName(this.getFullName())
                .email(this.getEmail())
                .type(this.getType())
                .roles(Collections.emptyList())
                .build();
    }

}

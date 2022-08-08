package com.bioautoml.domain.user.dto;

import com.bioautoml.domain.user.enums.Type;
import com.bioautoml.domain.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

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
                .build();
    }

}

package com.bioautoml.domain.role.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleForm {

    @NotBlank(message = "Role name should not blank")
    @NotNull(message = "Role name should not null")
    @NotEmpty(message = "Role name should not empty")
    private String roleName;

}

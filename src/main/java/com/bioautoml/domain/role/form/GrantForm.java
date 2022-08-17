package com.bioautoml.domain.role.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GrantForm {

    @NotEmpty(message = "The userId cannot be empty")
    @NotNull(message = "The userId cannot be null")
    @NotBlank(message = "The userId cannot be empty")
    private String userId;

    @NotEmpty(message = "The role cannot be empty")
    @NotNull(message = "The role cannot be null")
    @NotBlank(message = "The role cannot be empty")
    private String role;

}

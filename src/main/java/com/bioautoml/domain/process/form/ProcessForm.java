package com.bioautoml.domain.process.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessForm {

    @NotEmpty(message = "The process type cannot be empty")
    @NotNull(message = "The process type cannot be null")
    @NotBlank(message = "The process type cannot be empty")
    private String processType;

    @NotEmpty(message = "The user id cannot be empty")
    @NotNull(message = "The user id cannot be null")
    @NotBlank(message = "The user id cannot be empty")
    private String userId;

}

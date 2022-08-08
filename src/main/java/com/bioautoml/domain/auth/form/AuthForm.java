package com.bioautoml.domain.auth.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthForm {

    @NotEmpty(message = "The email cannot be empty")
    @NotNull(message = "The email cannot be null")
    @NotBlank(message = "The email cannot be empty")
    @Email(message = "This is not a valid email")
    private String email;

    @NotEmpty(message = "The password cannot be empty")
    @NotNull(message = "The password cannot be null")
    @NotBlank(message = "The password cannot be empty")
    private String password;

}

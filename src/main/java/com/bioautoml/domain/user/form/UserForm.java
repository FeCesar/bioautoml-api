package com.bioautoml.domain.user.form;

import com.bioautoml.domain.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "The name cannot be empty")
    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be empty")
    private String fullName;

    @NotEmpty(message = "The email cannot be empty")
    @NotNull(message = "The email cannot be null")
    @NotBlank(message = "The email cannot be empty")
    @Email(message = "This is not a valid email")
    private String email;

    @NotEmpty(message = "The password cannot be empty")
    @NotNull(message = "The password cannot be null")
    @NotBlank(message = "The password cannot be empty")
    private String password;

    public UserModel toModel(){
        return UserModel.builder()
                .fullName(this.getFullName())
                .email(this.getEmail())
                .password(this.getPassword())
                .build();
    }
}

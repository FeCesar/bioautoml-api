package com.bioautoml.domain.user.model;

import com.bioautoml.domain.user.dto.UserDTO;
import com.bioautoml.domain.user.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserModel {

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

    public UserDTO toDTO(){
        return UserDTO.builder()
                .id(this.getId())
                .fullName(this.getFullName())
                .email(this.getEmail())
                .type(this.getType())
                .build();
    }

}

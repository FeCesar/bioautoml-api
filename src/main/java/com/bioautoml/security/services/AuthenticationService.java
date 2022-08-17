package com.bioautoml.security.services;

import com.bioautoml.domain.role.enums.Role;
import com.bioautoml.domain.role.model.RoleModel;
import com.bioautoml.domain.user.model.UserModel;
import com.bioautoml.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email){
        UserModel userModel = this.userService.getByEmail(email).toModel();
        userModel.setPassword(this.userService.getEncryptedPassword(email));
        userModel.getRoles().add(RoleModel.builder()
                .id(UUID.fromString("623be3e8-10a8-4277-8d23-47c38c82f987"))
                .roleName(Role.ADMIN)
                .build());

        return userModel;
    }

}

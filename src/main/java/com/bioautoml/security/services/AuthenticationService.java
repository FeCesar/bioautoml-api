package com.bioautoml.security.services;

import com.bioautoml.domain.user.model.UserModel;
import com.bioautoml.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email){
        UserModel userModel = this.userService.getByEmail(email).toModel();
        userModel.setPassword(this.userService.getEncryptedPassword(email));

        return userModel;
    }

}

package com.bioautoml.domain.auth.service;

import com.bioautoml.domain.auth.dto.AuthDTO;
import com.bioautoml.domain.auth.form.AuthForm;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.exceptions.UnauthorizedException;
import com.bioautoml.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public AuthDTO login(AuthForm authForm){
        return Optional.of(this.userService.getByEmail(authForm.getEmail()))
                .stream()
                .filter(user -> SecurityUtil.compareEncode(authForm.getPassword(), this.userService.getEncryptedPassword(authForm.getEmail())))
                .findFirst()
                .map(auth -> AuthDTO.builder()
                        .token(UUID.randomUUID().toString())
                        .build())
                .orElseThrow(() -> new UnauthorizedException("Email or password is incorrect!"));
    }

}

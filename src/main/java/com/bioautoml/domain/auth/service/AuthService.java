package com.bioautoml.domain.auth.service;

import com.bioautoml.domain.auth.dto.AuthDTO;
import com.bioautoml.domain.auth.form.AuthForm;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.exceptions.UnauthorizedException;
import com.bioautoml.security.SecurityUtil;
import com.bioautoml.security.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthDTO login(AuthForm authForm){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = authForm.toAuthenticationToken();

        return Optional.of(this.userService.getByEmail(authForm.getEmail()))
                .stream()
                .filter(user -> SecurityUtil.compareEncode(authForm.getPassword(), this.userService.getEncryptedPassword(authForm.getEmail())))
                .findFirst()
                .map(auth -> AuthDTO.builder()
                        .token(this.jwtService.generateToken(authenticationManager.authenticate(usernamePasswordAuthenticationToken)))
                        .type("Bearer")
                        .build())
                .orElseThrow(() -> new UnauthorizedException("Email or password is incorrect!"));
    }

}

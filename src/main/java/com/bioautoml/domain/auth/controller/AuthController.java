package com.bioautoml.domain.auth.controller;

import com.bioautoml.domain.auth.dto.AuthDTO;
import com.bioautoml.domain.auth.form.AuthForm;
import com.bioautoml.domain.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid AuthForm authForm) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authService.login(authForm));
    }

}

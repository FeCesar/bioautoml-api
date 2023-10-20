package com.bioautoml.domain.auth.controller.impl;

import com.bioautoml.domain.auth.controller.AuthController;
import com.bioautoml.domain.auth.dto.AuthDTO;
import com.bioautoml.domain.auth.form.AuthForm;
import com.bioautoml.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Component
@RequiredArgsConstructor
@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    @CrossOrigin(value = "http://localhost:3000")
    public ResponseEntity<AuthDTO> login(AuthForm authForm) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authService.login(authForm));
    }

}

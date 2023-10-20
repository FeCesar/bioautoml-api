package com.bioautoml.domain.auth.controller;

import com.bioautoml.domain.auth.dto.AuthDTO;
import com.bioautoml.domain.auth.form.AuthForm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public interface AuthController {

    @PostMapping
    ResponseEntity<AuthDTO> login(@RequestBody @Valid AuthForm authForm);
}

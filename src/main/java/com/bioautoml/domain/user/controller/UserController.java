package com.bioautoml.domain.user.controller;

import com.bioautoml.domain.user.dto.UserDTO;
import com.bioautoml.domain.user.form.UserForm;
import com.bioautoml.domain.user.model.UserModel;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.security.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> save(@RequestBody @Valid UserForm userForm){
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userForm, userModel);

        userModel.setPassword(SecurityUtil.encode(userForm.getPassword()));

        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.save(userModel));
    }

}

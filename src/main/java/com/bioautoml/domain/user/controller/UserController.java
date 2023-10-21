package com.bioautoml.domain.user.controller;

import com.bioautoml.domain.process.dto.ProcessByUserDTO;
import com.bioautoml.domain.result.dto.ResultSimpleDTO;
import com.bioautoml.domain.role.dto.RoleDTO;
import com.bioautoml.domain.role.enums.Role;
import com.bioautoml.domain.role.model.RoleModel;
import com.bioautoml.domain.role.service.RoleService;
import com.bioautoml.domain.user.dto.UserDTO;
import com.bioautoml.domain.user.form.UserForm;
import com.bioautoml.domain.user.model.UserModel;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.security.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(nickname ="getAll", value = "Get all users", response = UserDTO[].class)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAll());
    }

    @ApiOperation(nickname ="getById", value = "Get user by Id", response = UserDTO.class)
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getById(id));
    }

    @ApiOperation(nickname ="getUserProcesses", value = "Get user processes by Id", response = ProcessByUserDTO[].class)
    @GetMapping("/{id}/processes")
    public ResponseEntity<List<ProcessByUserDTO>> getProcesses(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllServicesByUser(id));
    }

    @ApiOperation(nickname ="createUser", value = "Create user", response = UserDTO.class)
    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody @Valid UserForm userForm){
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userForm, userModel);

        List<RoleModel> userRoles = new ArrayList<>();
        RoleDTO roleDTO = roleService.getByName(Role.DEFAULT);

        userRoles.add(RoleModel.builder()
                .id(roleDTO.getId())
                .roleName(Role.DEFAULT)
                .build());

        userModel.setPassword(SecurityUtil.encode(userForm.getPassword()));
        userModel.setRoles(userRoles);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.save(userModel));
    }

    @ApiOperation(nickname ="getResults", value = "Get user results", response = ResultSimpleDTO[].class)
    @GetMapping("/{id}/results")
    public ResponseEntity<List<ResultSimpleDTO>> getResults(
        @PathVariable UUID id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllResultsBy(id));
    }

}

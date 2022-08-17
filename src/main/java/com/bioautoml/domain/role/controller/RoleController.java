package com.bioautoml.domain.role.controller;

import com.bioautoml.domain.role.dto.RoleDTO;
import com.bioautoml.domain.role.enums.Role;
import com.bioautoml.domain.role.form.GrantForm;
import com.bioautoml.domain.role.form.RoleForm;
import com.bioautoml.domain.role.model.RoleModel;
import com.bioautoml.domain.role.service.RoleService;
import com.bioautoml.domain.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/role")
@CrossOrigin(value = "*", maxAge = 3600)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<List<RoleDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.roleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.roleService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<RoleDTO> save(@RequestBody @Valid RoleForm roleForm){
        RoleModel roleModel = new RoleModel();
        roleModel.setRoleName(Role.valueOf(roleForm.getRoleName()));

        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.save(roleModel));
    }

    @PostMapping("/grant")
    public ResponseEntity<UserDTO> grant(@RequestBody @Valid GrantForm grantForm){
        return ResponseEntity.status(HttpStatus.OK).body(this.roleService.addGrant(UUID.fromString(grantForm.getUserId()),
                Role.valueOf(grantForm.getRole())));
    }

}

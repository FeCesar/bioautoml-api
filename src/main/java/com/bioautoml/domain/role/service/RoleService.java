package com.bioautoml.domain.role.service;

import com.bioautoml.domain.role.dto.RoleDTO;
import com.bioautoml.domain.role.enums.Role;
import com.bioautoml.domain.role.model.RoleModel;
import com.bioautoml.domain.role.repository.RoleRepository;
import com.bioautoml.domain.user.dto.UserDTO;
import com.bioautoml.domain.user.model.UserModel;
import com.bioautoml.domain.user.repository.UserRepository;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public List<RoleDTO> getAll(){
        return this.roleRepository.findAll()
                .stream()
                .map(RoleModel::toDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO getById(UUID id){
        return this.roleRepository.findById(id)
                .map(RoleModel::toDTO)
                .orElseThrow(() -> new NotFoundException("Role not exists!"));
    }

    public RoleDTO getByName(Role name){
        return this.roleRepository.findByRoleName(name)
                .map(RoleModel::toDTO)
                .orElseThrow(() -> new NotFoundException("Role not exists!"));
    }

    public RoleDTO save(RoleModel roleModel){
        return this.roleRepository.save(roleModel).toDTO();
    }

    public UserDTO addGrant(UUID userId, Role roleName){
        UserModel userModel = this.userRepository.findById(userId).get();
        RoleModel roleModel = this.roleRepository.findByRoleName(roleName).get();

        userModel.getRoles().add(roleModel);

        userModel.setRoles(userModel.getRoles()
                .stream()
                .distinct()
                .collect(Collectors.toList()));

        return this.userRepository.save(userModel).toDTO();
    }

}

package com.bioautoml.domain.user.service;

import com.bioautoml.domain.process.dto.ProcessByUserDTO;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.repository.ProcessRepository;
import com.bioautoml.domain.user.dto.UserDTO;
import com.bioautoml.domain.user.model.UserModel;
import com.bioautoml.domain.user.repository.UserRepository;
import com.bioautoml.exceptions.AlreadyExistsException;
import com.bioautoml.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProcessRepository processRepository;

    public List<UserDTO> getAll(){
        return this.userRepository.findAll()
                .stream()
                .map(UserModel::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getById(UUID id){
        return this.userRepository.findById(id)
                .stream()
                .map(UserModel::toDTO)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not exists!"));
    }

    public UserDTO getByEmail(String email){
        return this.userRepository.findByEmail(email)
                .stream()
                .map(UserModel::toDTO)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not exists!"));
    }

    public UserDTO save(UserModel userModel) {
        this.userRepository.findByEmail(userModel.getEmail())
                .ifPresent(user -> {
                    throw new AlreadyExistsException("Email already exists!");
                });

        return this.userRepository.save(userModel).toDTO();
    }

    public String getEncryptedPassword(String email){
        return this.userRepository.findByEmail(email)
                .stream()
                .map(UserModel::getPassword)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not exists!"));
    }

    public List<ProcessByUserDTO> getAllServicesByUser(UUID userId) {
        this.getById(userId);
        return this.processRepository.findByUserId(userId)
                .stream()
                .map(ProcessModel::toProcessByUserDTO)
                .collect(Collectors.toList());
    }
}

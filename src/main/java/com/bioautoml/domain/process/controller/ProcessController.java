package com.bioautoml.domain.process.controller;

import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.process.form.ProcessForm;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.service.ProcessService;
import com.bioautoml.domain.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<ProcessDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessDTO> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ProcessDTO> start(@RequestBody @Valid ProcessForm processForm){
        ProcessModel processModel = new ProcessModel();
        processModel.setProcessType(ProcessType.valueOf(processForm.getProcessType()));
        processModel.setUserModel(this.userService.getById(UUID.fromString(processForm.getUserId())).toModel());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.processService.start(processModel));

    }

}

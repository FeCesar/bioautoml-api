package com.bioautoml.domain.process.parameters.controller;

import com.bioautoml.domain.process.parameters.dto.AFEMDTO;
import com.bioautoml.domain.process.parameters.dto.AFEMResponseDTO;
import com.bioautoml.domain.process.parameters.dto.MetalearningDTO;
import com.bioautoml.domain.process.parameters.dto.MetalearningResponseDTO;
import com.bioautoml.domain.process.parameters.service.strategy.AFEMServiceStrategy;
import com.bioautoml.domain.process.parameters.service.strategy.MetalearningServiceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/parameters")
public class ParametersController {

    @Autowired
    private AFEMServiceStrategy afemService;

    @Autowired
    private MetalearningServiceStrategy metalearningService;

    @GetMapping("/afem")
    public ResponseEntity<List<AFEMDTO>> getAllAfem(){
        return ResponseEntity.status(HttpStatus.OK).body(this.afemService.getAll());
    }

    @GetMapping("/afem/{processId}")
    public ResponseEntity<AFEMResponseDTO> getByProcessIdAfem(@PathVariable UUID processId){
        return ResponseEntity.status(HttpStatus.OK).body(this.afemService.getByProcessId(processId));
    }

    @GetMapping("/metalearning")
    public ResponseEntity<List<MetalearningDTO>> getAllMetalearning(){
        return ResponseEntity.status(HttpStatus.OK).body(this.metalearningService.getAll());
    }

    @GetMapping("/metalearning/{processId}")
    public ResponseEntity<MetalearningResponseDTO> getByProcessIdMetalearning(@PathVariable UUID processId){
        return ResponseEntity.status(HttpStatus.OK).body(this.metalearningService.getByProcessId(processId));
    }

}

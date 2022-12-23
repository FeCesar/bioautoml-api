package com.bioautoml.domain.process.controller;

import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.form.AFEMForm;
import com.bioautoml.domain.process.form.MetalearningForm;
import com.bioautoml.domain.process.parameters.dto.AFEMDTO;
import com.bioautoml.domain.process.parameters.dto.MetalearningDTO;
import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import com.bioautoml.domain.process.parameters.service.ParametersService;
import com.bioautoml.domain.process.service.ProcessService;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.security.services.JwtService;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/processes")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private UserService userService;

    @Autowired
    private ParametersService parametersService;

    @Autowired
    private JwtService jwtService;

    private final Gson gson = new Gson();


    @GetMapping("/")
    public ResponseEntity<List<ProcessDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessDTO> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getById(id));
    }

    @PostMapping(value = "/afem/{processName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProcessDTO> afemStart(
            @PathVariable String processName,
            @RequestPart MultipartFile[] files,
            @RequestParam String parameters,
            @RequestHeader(value = "Authorization") String token) {
        UUID userId = this.jwtService.getUserId(token.split(" ")[1]);

        ParametersEntity parametersEntity = new AFEMDTO();
        AFEMForm afemForm = this.gson.fromJson(parameters, AFEMForm.class);
        BeanUtils.copyProperties(afemForm, parametersEntity);

        return ResponseEntity.status(HttpStatus.OK).body(this.processService.start(processName, Arrays.asList(files), userId, parametersEntity));
    }

    @PostMapping(value = "/metalearning/{processName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProcessDTO> metalearningStart(
            @PathVariable String processName,
            @RequestPart MultipartFile[] files,
            @RequestParam String parameters,
            @RequestHeader(value = "Authorization") String token) {
        UUID userId = this.jwtService.getUserId(token.split(" ")[1]);

        ParametersEntity parametersEntity = new MetalearningDTO();
        MetalearningForm metalearningForm = this.gson.fromJson(parameters, MetalearningForm.class);
        BeanUtils.copyProperties(metalearningForm, parametersEntity);

        return ResponseEntity.status(HttpStatus.OK).body(this.processService.start(processName, Arrays.asList(files), userId, parametersEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id){
        this.processService.updateStatus(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

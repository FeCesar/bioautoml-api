package com.bioautoml.domain.process.controller;

import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.process.parameters.enums.Classifiers;
import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import com.bioautoml.domain.process.parameters.service.ParametersService;
import com.bioautoml.domain.process.service.ProcessService;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.security.services.JwtService;
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


    @GetMapping("/")
    public ResponseEntity<List<ProcessDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessDTO> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getById(id));
    }

    @PostMapping(value = "/{processName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProcessDTO> start(
            @PathVariable String processName,
            @RequestPart MultipartFile[] files,
            @RequestParam(required = false) String fastaTrain,
            @RequestParam(required = false) String fastaLabelTrain,
            @RequestParam(required = false) String fastaTest,
            @RequestParam(required = false) String fastaLabelTest,
            @RequestParam(required = false) String train,
            @RequestParam(required = false) String trainLabel,
            @RequestParam(required = false) String test,
            @RequestParam(required = false) String testLabel,
            @RequestParam(required = false) String testNameEq,
            @RequestParam(required = false) Classifiers classifiers,
            @RequestParam(required = false) Boolean normalization,
            @RequestParam(required = false) Boolean imbalance,
            @RequestParam(required = false) Boolean tuning,
            @RequestHeader(value = "Authorization") String token) {
        UUID userId = this.jwtService.getUserId(token.split(" ")[1]);

        ParametersEntity parameters = this.parametersService.createParameterServiceObject(ProcessType.valueOf(processName), fastaTrain, fastaLabelTrain,
                fastaTest, fastaLabelTest, train, trainLabel, test, testLabel, testNameEq, classifiers, normalization, imbalance, tuning);

        return ResponseEntity.status(HttpStatus.OK).body(this.processService.start(processName, Arrays.asList(files), userId, parameters));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id){
        this.processService.updateStatus(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

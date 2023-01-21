package com.bioautoml.domain.process.controller;

import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.parameters.form.AFEMForm;
import com.bioautoml.domain.process.parameters.form.MetalearningForm;
import com.bioautoml.domain.process.parameters.service.ParametersService;
import com.bioautoml.domain.process.service.ProcessService;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.security.services.JwtService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
            @RequestPart MultipartFile[] train,
            @RequestPart MultipartFile[] train_label,
            @RequestPart MultipartFile[] test,
            @RequestPart MultipartFile[] test_label,
            @RequestParam AFEMForm parameters,
            @RequestHeader(value = "Authorization") String token) {
        UUID userId = this.jwtService.getUserId(token.split(" ")[1]);

        Map<String, MultipartFile[]> files = new HashMap<>();
        files.put("train", train);
        files.put("trainLabel", train_label);
        files.put("test", test);
        files.put("testLabel", test_label);

        return ResponseEntity.status(HttpStatus.OK).body(this.processService.start(processName, files, userId, parameters));
    }

    @PostMapping(value = "/metalearning/{processName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProcessDTO> metalearningStart(
            @PathVariable String processName,
            @RequestPart MultipartFile[] train,
            @RequestPart MultipartFile[] train_label,
            @RequestPart MultipartFile[] test,
            @RequestPart MultipartFile[] test_label,
            @RequestPart MultipartFile[] test_name_sequence,
            @RequestParam MetalearningForm parameters,
            @RequestHeader(value = "Authorization") String token) {
        UUID userId = this.jwtService.getUserId(token.split(" ")[1]);

        Map<String, MultipartFile[]> files = new HashMap<>();
        files.put("train", train);
        files.put("trainLabel", train_label);
        files.put("test", test);
        files.put("testLabel", test_label);
        files.put("testNameSequence", test_name_sequence);

        return ResponseEntity.status(HttpStatus.OK).body(this.processService.start(processName, files, userId, parameters));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id){
        this.processService.updateStatus(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

package com.bioautoml.domain.process.controller;

import com.bioautoml.domain.process.dto.ProcessAggregatedDTO;
import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.parameters.form.AFEMForm;
import com.bioautoml.domain.process.parameters.form.LabelForm;
import com.bioautoml.domain.process.parameters.form.MetalearningForm;
import com.bioautoml.domain.process.parameters.service.ParameterService;
import com.bioautoml.domain.process.service.ProcessAggregatedService;
import com.bioautoml.domain.process.service.ProcessService;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.security.services.JwtService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/processes")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private UserService userService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private ProcessAggregatedService processAggregatedService;

    @Autowired
    private JwtService jwtService;

    private final Gson gson = new Gson();


    @ApiOperation(nickname ="getAllProcesses", value = "Get all process", response = ProcessDTO[].class)
    @GetMapping
    public ResponseEntity<List<ProcessDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getAll());
    }

    @ApiOperation(nickname ="getProcessById", value = "Get process by Id", response = ProcessDTO.class)
    @GetMapping("/{id}")
    public ResponseEntity<ProcessDTO> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getById(id));
    }
    @ApiOperation(nickname ="afemStart", value = "Start AFEM process", response = ProcessDTO.class)
    @PostMapping(value = "/afem/{processName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProcessDTO> afemStart(
            @PathVariable String processName,
            @RequestPart MultipartFile[] train,
            @RequestPart(required = false) MultipartFile[] test,
            @RequestParam(required = false) String parameters,
            @RequestParam String labels,
            @RequestParam String referenceName,
            @RequestHeader(value = "Authorization") String token) {
        UUID userId = this.jwtService.getUserId(token.split(" ")[1]);

        Map<String, MultipartFile[]> files = new HashMap<>();
        files.put("TRAIN", train);

        if(test != null) {
            files.put("TEST", test);
        }

        LabelForm labelForm = this.gson.fromJson(labels, LabelForm.class);
        AFEMForm afemForm = this.gson.fromJson(parameters, AFEMForm.class);

        return ResponseEntity.status(HttpStatus.OK).body(
            this.processService.start(
                processName,
                files,
                userId,
                afemForm,
                labelForm,
                referenceName
            )
        );
    }
    @ApiOperation(nickname ="metalearningStart", value = "Start METALEARNING process", response = ProcessDTO.class)

    @PostMapping(value = "/metalearning/{processName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProcessDTO> metalearningStart(
            @PathVariable String processName,
            @RequestPart MultipartFile[] train,
            @RequestParam MultipartFile[] train_label,
            @RequestPart(required = false) MultipartFile[] test,
            @RequestParam(required = false) MultipartFile[] test_label,
            @RequestPart MultipartFile[] test_name_sequence,
            @RequestParam String parameters,
            @RequestParam String referenceName,
            @RequestHeader(value = "Authorization") String token) {
        UUID userId = this.jwtService.getUserId(token.split(" ")[1]);

        Map<String, MultipartFile[]> files = new HashMap<>();
        files.put("TRAIN", train);
        files.put("LABEL_TRAIN", train_label);
        files.put("SEQUENCE", test_name_sequence);

        if(test != null) {
            files.put("TEST", test);
            files.put("LABEL_TEST", test_label);
        }

        MetalearningForm metalearningForm = this.gson.fromJson(parameters, MetalearningForm.class);

        LabelForm labelForm = new LabelForm();
        labelForm.setTestLabels(Collections.emptyList());

        return ResponseEntity.status(HttpStatus.OK).body(
            this.processService.start(
                processName,
                files,
                userId,
                metalearningForm,
                labelForm,
                referenceName
            )
        );
    }
    @ApiOperation(nickname ="getAllFromProcessBy", value = "Get aggregated processes", response = ProcessAggregatedDTO.class)
    @GetMapping(value = "/{id}/aggregated")
    public ResponseEntity<ProcessAggregatedDTO> getAllFromProcessBy(
        @PathVariable UUID id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.processAggregatedService.getAllInfoFrom(id));
    }

}

package com.bioautoml.domain.process.controller;

import com.bioautoml.domain.process.dto.ProcessAggregatedDTO;
import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.parameters.form.AFEMForm;
import com.bioautoml.domain.process.parameters.form.LabelForm;
import com.bioautoml.domain.process.parameters.form.MetalearningForm;
import com.bioautoml.domain.process.parameters.service.ParameterService;
import com.bioautoml.domain.process.service.ProcessAggregatedService;
import com.bioautoml.domain.process.service.ProcessService;
import com.google.gson.Gson;
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
    private ParameterService parameterService;

    @Autowired
    private ProcessAggregatedService processAggregatedService;

    private final Gson gson = new Gson();

    @CrossOrigin(value = "*")
    @GetMapping
    public ResponseEntity<List<ProcessDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getAll());
    }
    @CrossOrigin(value = "*")
    @GetMapping("/{id}")
    public ResponseEntity<ProcessDTO> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getById(id));
    }

    @CrossOrigin(value = "*")
    @GetMapping("/filter/{email}")
    public ResponseEntity<List<ProcessDTO>> getById(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getByEmail(email));
    }
    @CrossOrigin(value = "*")
    @PostMapping(value = "/afem/{processName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProcessDTO> afemStart(
            @PathVariable String processName,
            @RequestPart MultipartFile[] train,
            @RequestPart(required = false) MultipartFile[] test,
            @RequestParam(required = false) String parameters,
            @RequestParam String labels,
            @RequestParam String referenceName,
            @RequestParam String email) {
        Map<String, MultipartFile[]> files = new HashMap<>();
        files.put("TRAIN", train);

        if (test != null) {
            files.put("TEST", test);
        }

        LabelForm labelForm = this.gson.fromJson(labels, LabelForm.class);
        AFEMForm afemForm = this.gson.fromJson(parameters, AFEMForm.class);

        return ResponseEntity.status(HttpStatus.OK).body(
                this.processService.start(
                        processName,
                        files,
                        afemForm,
                        labelForm,
                        email,
                        referenceName
                )
        );
    }
    @CrossOrigin(value = "*")
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
            @RequestParam String email) {
        Map<String, MultipartFile[]> files = new HashMap<>();
        files.put("TRAIN", train);
        files.put("LABEL_TRAIN", train_label);
        files.put("SEQUENCE", test_name_sequence);

        if (test != null) {
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
                        metalearningForm,
                        labelForm,
                        email,
                        referenceName
                )
        );
    }
    @CrossOrigin(value = "*")
    @GetMapping(value = "/{id}/aggregated")
    public ResponseEntity<ProcessAggregatedDTO> getAllFromProcessBy(
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.processAggregatedService.getAllInfoFrom(id));
    }

}

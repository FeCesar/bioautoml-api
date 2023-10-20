package com.bioautoml.domain.process.controller.impl;

import com.bioautoml.domain.process.controller.ProcessController;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Component
public class ProcessControllerImpl implements ProcessController {
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


    @Override
    public ResponseEntity<List<ProcessDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getAll());
    }

    @Override
    public ResponseEntity<ProcessDTO> getById(UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.getById(id));
    }

    @Override
    public ResponseEntity<ProcessDTO> afemStart(
            String processName,
            MultipartFile[] train,
            MultipartFile[] test,
            String parameters,
            String labels,
            String referenceName,
            String token) {
        UUID userId = this.jwtService.getUserId(token.split(" ")[1]);

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
                        userId,
                        afemForm,
                        labelForm,
                        referenceName
                )
        );
    }

    @Override
    public ResponseEntity<ProcessDTO> metalearningStart(String processName,
                                                        MultipartFile[] train,
                                                        MultipartFile[] train_label,
                                                        MultipartFile[] test,
                                                        MultipartFile[] test_label,
                                                        MultipartFile[] test_name_sequence,
                                                        String parameters,
                                                        String referenceName,
                                                        String token) {
        UUID userId = this.jwtService.getUserId(token.split(" ")[1]);

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
                        userId,
                        metalearningForm,
                        labelForm,
                        referenceName
                )
        );
    }

    @Override
    public ResponseEntity<ProcessAggregatedDTO> getAllFromProcessBy(UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.processAggregatedService.getAllInfoFrom(id));
    }

}

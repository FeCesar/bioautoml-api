package com.bioautoml.domain.process.controller;

import com.bioautoml.domain.process.dto.ProcessAggregatedDTO;
import com.bioautoml.domain.process.dto.ProcessDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/processes")
public interface ProcessController {
    @GetMapping
    ResponseEntity<List<ProcessDTO>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<ProcessDTO> getById(@PathVariable(value = "id") UUID id);

    @PostMapping(value = "/metalearning/{processName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<ProcessDTO> metalearningStart(
            @PathVariable(value = "processName") String processName,
            @RequestPart MultipartFile[] train,
            @RequestParam MultipartFile[] train_label,
            @RequestPart(required = false) MultipartFile[] test,
            @RequestParam(required = false) MultipartFile[] test_label,
            @RequestPart MultipartFile[] test_name_sequence,
            @RequestParam String parameters,
            @RequestParam String referenceName,
            @RequestHeader(value = "Authorization") String token);

    @PostMapping(value = "/afem/{processName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<ProcessDTO> afemStart(
            @PathVariable(value = "processName") String processName,
            @RequestPart MultipartFile[] train,
            @RequestPart(required = false) MultipartFile[] test,
            @RequestParam(required = false) String parameters,
            @RequestParam String labels,
            @RequestParam String referenceName,
            @RequestHeader(value = "Authorization") String token);

    @GetMapping(value = "/{id}/aggregated")
    ResponseEntity<ProcessAggregatedDTO> getAllFromProcessBy(
            @PathVariable(value = "id") UUID id
    );

}

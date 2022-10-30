package com.bioautoml.domain.process.controller;

import com.bioautoml.domain.process.dto.ProcessDTO;
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
    public ResponseEntity<ProcessDTO> start(@PathVariable String processName, @RequestPart MultipartFile[] files, @RequestHeader(value = "Authorization") String token){
        UUID userId = this.jwtService.getUserId(token.split(" ")[1]);
        return ResponseEntity.status(HttpStatus.OK).body(this.processService.start(processName, Arrays.asList(files), userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id){
        this.processService.updateStatus(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

package com.bioautoml.domain.error.service;

import com.bioautoml.domain.error.dto.ErrorDTO;
import com.bioautoml.domain.error.model.ErrorModel;
import com.bioautoml.domain.error.repository.ErrorModelRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

@Service
public class ErrorService {

    @Autowired
    private ErrorModelRepository errorModelRepository;
    private final Gson gson = new Gson();

    private static final Logger logger = LoggerFactory.getLogger(ErrorService.class);

    public void decode(String encodedError) {
        try{
            String errorJson = new String(Base64.getDecoder().decode(encodedError));
            ErrorDTO errorDTO = this.gson.fromJson(errorJson, ErrorDTO.class);

            this.save(errorDTO);
        } catch (Exception e) {
            logger.error("error from job can not deseralizable={}", encodedError);
        }
    }

    public void save(ErrorDTO errorDTO) {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setType(errorDTO.getErrorType());
        errorModel.setMessage(errorDTO.getMessage());
        errorModel.setProcessId(UUID.fromString(errorDTO.getProcessId()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime referenceDate = LocalDateTime.parse(errorDTO.getReferenceDate(), formatter);
        errorModel.setReferenceDate(referenceDate);

        this.errorModelRepository.save(errorModel);
    }

}

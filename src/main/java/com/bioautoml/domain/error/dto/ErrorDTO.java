package com.bioautoml.domain.error.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO implements Serializable {

    private String errorType;
    private String message;
    private String processId;
    private String referenceDate;

}

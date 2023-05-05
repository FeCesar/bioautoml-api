package com.bioautoml.domain.result.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultTransferDTO implements Serializable {

    private String processId;
    private String referenceDate;

}

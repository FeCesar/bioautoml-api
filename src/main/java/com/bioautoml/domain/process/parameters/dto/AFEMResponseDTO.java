package com.bioautoml.domain.process.parameters.dto;

import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AFEMResponseDTO implements ParametersEntity {

    public UUID id;
    public String fastaTrain;
    public String fastaLabelTrain;
    public String fastaTest;
    public String fastaLabelTest;
    public Integer estimations = 50;
    public Integer cpuNumbers = 1;
    public String output;

}

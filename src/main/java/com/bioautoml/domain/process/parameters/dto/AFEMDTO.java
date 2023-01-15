package com.bioautoml.domain.process.parameters.dto;

import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.parameters.model.AFEMModel;
import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import com.bioautoml.domain.process.parameters.vo.AFEMParameterVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AFEMDTO implements ParametersEntity {

    public UUID id;
    public String fastaTrain;
    public String fastaLabelTrain;
    public String fastaTest;
    public String fastaLabelTest;
    public Integer estimations = 50;
    public Integer cpuNumbers = 1;
    public String output;
    public ProcessDTO process;

    public AFEMModel toModel(){
        return AFEMModel.builder()
                .id(this.getId())
                .fastaTrain(this.getFastaTrain())
                .fastaLabelTrain(this.getFastaLabelTrain())
                .fastaTest(this.getFastaTest())
                .fastaLabelTest(this.getFastaLabelTest())
                .estimations(this.getEstimations())
                .cpuNumbers(this.getCpuNumbers())
                .output(this.getOutput())
                .process(this.getProcess().toModel())
                .build();
    }

    public AFEMParameterVO toVO(){
        return AFEMParameterVO.builder()
                .id(this.getId())
                .fastaTrain(this.getFastaTrain())
                .fastaLabelTrain(this.getFastaLabelTrain())
                .fastaTest(this.getFastaTest())
                .fastaLabelTest(this.getFastaLabelTest())
                .estimations(this.getEstimations())
                .cpuNumbers(this.getCpuNumbers())
                .output(this.getOutput())
                .processId(this.getProcess().getId())
                .build();
    }

}

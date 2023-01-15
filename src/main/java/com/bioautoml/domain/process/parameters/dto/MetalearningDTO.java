package com.bioautoml.domain.process.parameters.dto;

import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.parameters.enums.Classifiers;
import com.bioautoml.domain.process.parameters.model.MetalearningModel;
import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import com.bioautoml.domain.process.parameters.vo.MetalearningParameterVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetalearningDTO implements ParametersEntity {

    private UUID id;
    private String train;
    private String trainLabel;
    private String test;
    private String testLabel;
    private String testNamesEq;
    private Boolean normalization = false;
    private Integer cpuNumbers = 1;
    private Classifiers classifiers;
    private Boolean imbalance = false;
    private Boolean tuning = false;
    public String output;
    private ProcessDTO process;

    public MetalearningModel toModel(){
        return MetalearningModel.builder()
                .id(this.getId())
                .train(this.getTrain())
                .trainLabel(this.getTrainLabel())
                .test(this.getTest())
                .testLabel(this.getTestLabel())
                .testNamesEq(this.getTestNamesEq())
                .normalization(this.getNormalization())
                .cpuNumbers(this.getCpuNumbers())
                .classifiers(this.getClassifiers())
                .imbalance(this.getImbalance())
                .tuning(this.getTuning())
                .output(this.getOutput())
                .process(this.getProcess().toModel())
                .build();
    }

    public MetalearningParameterVO toVO(){
        return MetalearningParameterVO.builder()
                .id(this.getId())
                .train(this.getTrain())
                .trainLabel(this.getTrainLabel())
                .test(this.getTest())
                .testLabel(this.getTestLabel())
                .testNamesEq(this.getTestNamesEq())
                .normalization(this.getNormalization())
                .cpuNumbers(this.getCpuNumbers())
                .classifiers(this.getClassifiers())
                .imbalance(this.getImbalance())
                .tuning(this.getTuning())
                .output(this.getOutput())
                .processId(this.getProcess().getId())
                .build();
    }

}

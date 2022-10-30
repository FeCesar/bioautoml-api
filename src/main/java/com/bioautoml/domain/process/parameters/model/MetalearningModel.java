package com.bioautoml.domain.process.parameters.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.dto.MetalearningDTO;
import com.bioautoml.domain.process.parameters.dto.MetalearningResponseDTO;
import com.bioautoml.domain.process.parameters.enums.Classifiers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metalearning_parameters")
public class MetalearningModel implements ParametersEntity, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "train", nullable = false)
    private String train;

    @Column(name = "train_label")
    private String trainLabel;

    @Column(name = "test")
    private String test;

    @Column(name = "test_label")
    private String testLabel;

    @Column(name = "test_names_eq")
    private String testNamesEq;

    @Column(name = "normalization", nullable = false)
    private Boolean normalization = false;

    @Column(name = "cpu_numbers")
    private Integer cpuNumbers = 1;

    @Column(name = "classifier")
    @Enumerated(EnumType.ORDINAL)
    private Classifiers classifiers;

    @Column(name = "imbalance")
    private Boolean imbalance = false;

    @Column(name = "tuning")
    private Boolean tuning = false;

    @Column(name = "output")
    public String output;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private ProcessModel process;

    public MetalearningDTO toDTO(){
        return MetalearningDTO.builder()
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
                .process(this.getProcess().toDTO())
                .build();
    }

    public MetalearningResponseDTO toResponseDTO(){
        return MetalearningResponseDTO.builder()
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
                .build();
    }

}

















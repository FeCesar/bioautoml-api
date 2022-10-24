package com.bioautoml.domain.process.parameters.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.dto.AFEMDTO;
import com.bioautoml.domain.process.parameters.dto.AFEMResponseDTO;
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
@Table(name = "afem_parameters")
public class AFEMModel implements ParametersEntity, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @Column(name = "fasta_train", nullable = false)
    public String fastaTrain;

    @Column(name = "fasta_label_train")
    public String fastaLabelTrain;

    @Column(name = "fasta_test", nullable = false)
    public String fastaTest;

    @Column(name = "fasta_label_test")
    public String fastaLabelTest;

    @Column(name = "estimations")
    public Integer estimations = 50;

    @Column(name = "cpu_numbers")
    public Integer cpuNumbers = 1;

    @Column(name = "output")
    public String output;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private ProcessModel process;

    public AFEMDTO toDTO(){
        return AFEMDTO.builder()
                .id(this.getId())
                .fastaTrain(this.getFastaTrain())
                .fastaLabelTrain(this.getFastaLabelTrain())
                .fastaTest(this.getFastaTest())
                .fastaLabelTest(this.getFastaLabelTest())
                .estimations(this.getEstimations())
                .cpuNumbers(this.getCpuNumbers())
                .output(this.getOutput())
                .process(this.getProcess().toDTO())
                .build();
    }

    public AFEMResponseDTO toResponseDTO(){
        return AFEMResponseDTO.builder()
                .id(this.getId())
                .fastaTrain(this.getFastaTrain())
                .fastaLabelTrain(this.getFastaLabelTrain())
                .fastaTest(this.getFastaTest())
                .fastaLabelTest(this.getFastaLabelTest())
                .estimations(this.getEstimations())
                .cpuNumbers(this.getCpuNumbers())
                .output(this.getOutput())
                .build();
    }
}

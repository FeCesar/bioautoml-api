package com.bioautoml.domain.process.parameters.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.dto.AFEMResponseDTO;
import com.bioautoml.domain.process.parameters.dto.AFEMSimpleDTO;
import com.bioautoml.domain.process.parameters.vo.AFEMParameterVO;
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

    @Column(name = "estimations")
    public Integer estimations = 50;

    @Column(name = "cpu_numbers")
    public Integer cpuNumbers = 1;

    @Column(name = "output")
    public String output;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private ProcessModel process;

    public AFEMResponseDTO toResponseDTO(){
        return AFEMResponseDTO.builder()
                .id(this.getId())
                .estimations(this.getEstimations())
                .cpuNumbers(this.getCpuNumbers())
                .output(this.getOutput())
                .build();
    }

    public AFEMParameterVO toVO(){
        return AFEMParameterVO.builder()
                .id(this.getId())
                .estimations(this.getEstimations())
                .cpuNumbers(this.getCpuNumbers())
                .output(this.getOutput())
                .processId(this.getProcess().getId())
                .build();
    }

    public AFEMSimpleDTO toSimpleDTO(){
        return AFEMSimpleDTO.builder()
                .cpuNumbers(this.getCpuNumbers())
                .estimations(this.getEstimations())
                .build();
    }
}

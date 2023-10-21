package com.bioautoml.domain.process.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.file.model.FileModel;
import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.dto.ProcessSimpleDTO;
import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.enums.ProcessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "processes")
public class ProcessModel implements BaseEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessType processType;

    @Column(nullable = false)
    private LocalDateTime startupTime = LocalDateTime.now();

    @Column(name = "reference_name")
    private String referenceName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus = ProcessStatus.WAITING;

    @Column
    private LocalDateTime completionTime;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "processModel")
    private List<FileModel> files = new ArrayList<>();


    public ProcessDTO toDTO(){
        return ProcessDTO.builder()
                .id(this.getId())
                .processType(this.getProcessType())
                .processStatus(this.getProcessStatus())
                .startupTime(this.getStartupTime())
                .completionTime((this.getCompletionTime()))
                .referenceName(this.getReferenceName())
                .build();
    }

    public ProcessByUserDTO toProcessByUserDTO(){
        return ProcessByUserDTO.builder()
                .id(this.getId())
                .processType(this.getProcessType())
                .processStatus(this.getProcessStatus())
                .startupTime(this.getStartupTime())
                .completionTime((this.getCompletionTime()))
                .build();
    }

    public ProcessSimpleDTO toSimpleDTO(){
        return ProcessSimpleDTO.builder()
                .referenceName(this.getReferenceName())
                .completionTime(this.getCompletionTime())
                .processStatus(this.getProcessStatus())
                .processType(this.getProcessType())
                .startupTime(this.getStartupTime())
                .build();
    }

}

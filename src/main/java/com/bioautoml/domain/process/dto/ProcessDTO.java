package com.bioautoml.domain.process.dto;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.process.model.ProcessModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDTO implements BaseEntity {

    private Long id;
    private ProcessType processType;
    private ProcessStatus processStatus;
    private LocalDateTime startupTime;
    private LocalDateTime completionTime;
    private String referenceName;
    private String email;

    public ProcessModel toModel(){
        return ProcessModel.builder()
                .id(this.getId())
                .processType(this.getProcessType())
                .processStatus(this.getProcessStatus())
                .startupTime(this.getStartupTime())
                .completionTime(this.getCompletionTime())
                .email(this.getEmail())
                .referenceName(this.getReferenceName())
                .build();
    }

    public ProcessDTO fromModel(ProcessModel model){
        return ProcessDTO.builder()
                .id(model.getId())
                .processType(model.getProcessType())
                .processStatus(model.getProcessStatus())
                .startupTime(model.getStartupTime())
                .completionTime(model.getCompletionTime())
                .email(model.getEmail())
                .referenceName(model.getReferenceName())
                .build();
    }

}

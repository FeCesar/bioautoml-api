package com.bioautoml.domain.process.dto;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.user.dto.UserProcessDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDTO implements BaseEntity {

    private UUID id;
    private ProcessType processType;
    private ProcessStatus processStatus;
    private Long startupTime;
    private Long completionTime;
    private UserProcessDTO user;

    public ProcessModel toModel(){
        return ProcessModel.builder()
                .id(this.getId())
                .processType(this.getProcessType())
                .processStatus(this.getProcessStatus())
                .startupTime(this.getStartupTime())
                .completionTime(this.getCompletionTime())
                .userModel(this.user.toModel())
                .build();
    }

}

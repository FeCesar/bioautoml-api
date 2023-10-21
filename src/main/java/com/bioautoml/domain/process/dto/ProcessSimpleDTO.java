package com.bioautoml.domain.process.dto;

import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.enums.ProcessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessSimpleDTO implements Serializable {

    private String referenceName;
    private ProcessType processType;
    private ProcessStatus processStatus;
    private LocalDateTime startupTime;
    private LocalDateTime completionTime;
    private String email;

}

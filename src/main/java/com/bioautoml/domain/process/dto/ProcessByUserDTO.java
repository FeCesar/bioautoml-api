package com.bioautoml.domain.process.dto;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.enums.ProcessType;
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
public class ProcessByUserDTO implements BaseEntity {

    private UUID id;
    private ProcessType processType;
    private ProcessStatus processStatus;
    private LocalDateTime startupTime;
    private LocalDateTime completionTime;

}

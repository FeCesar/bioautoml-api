package com.bioautoml.domain.result.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResultSimpleDTO implements Serializable {

    private Long processId;
    private String processName;
    private String link;
    private LocalDateTime creationDate;

}

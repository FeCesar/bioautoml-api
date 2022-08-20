package com.bioautoml.domain.process.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultObjectCreationRequestTemplateDTO implements Serializable {

    private UUID processId;
    private UUID userId;
    private Integer resultsFields;

}

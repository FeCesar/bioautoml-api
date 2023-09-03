package com.bioautoml.domain.result.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ResultComposeAggregatedDTO implements Serializable {

    private String downloadLink;
    private LocalDateTime creationDate;

}

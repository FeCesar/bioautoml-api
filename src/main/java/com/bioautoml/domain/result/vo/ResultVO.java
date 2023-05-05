package com.bioautoml.domain.result.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO implements Serializable {

    private UUID processId;
    private String link;
    private LocalDateTime referenceDate;

}
